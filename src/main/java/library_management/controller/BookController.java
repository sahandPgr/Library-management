package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library_management.dto.BookDto;
import library_management.entity.Book;
import library_management.service.AuthorService;
import library_management.service.BookService;
import library_management.service.CategoryService;
import library_management.service.PublisherService;

@Controller
@RequestMapping("/books")
public class BookController {

        private final BookService bookService;
        private final AuthorService authorService;
        private final PublisherService publisherService;
        private final CategoryService categoryService;

        public BookController(
                        BookService bookService,
                        AuthorService authorService,
                        PublisherService publisherService,
                        CategoryService categoryService) {

                this.bookService = bookService;
                this.authorService = authorService;
                this.publisherService = publisherService;
                this.categoryService = categoryService;
        }

        private BookDto convertToDto(Book book) {

                BookDto dto = new BookDto();

                dto.setId(book.getId());
                dto.setTitle(book.getTitle());
                dto.setIsbn(book.getIsbn());
                dto.setPublishYear(book.getPublishYear());
                dto.setQuantity(book.getQuantity());
                dto.setAuthorId(book.getAuthor().getId());
                dto.setPublisherId(book.getPublisher().getId());
                dto.setCategoryId(book.getCategory().getId());

                return dto;
        }

        @GetMapping
        public String books(Model model) {

                model.addAttribute("books", bookService.getAvailableBooks());

                return "books";
        }

        @GetMapping("/create")
        public String createBook(@RequestParam(required = false) String returnTo, Model model) {

                model.addAttribute("book", new BookDto());
                model.addAttribute("returnTo", returnTo);
                model.addAttribute("authors",
                                authorService.getAllAuthors());

                model.addAttribute("publishers",
                                publisherService.getAllPublishers());

                model.addAttribute("categories",
                                categoryService.getAllCategories());
                return "book-form";
        }

        @PostMapping("/save")
        public String saveBook(@RequestParam(required = false) String returnTo, @ModelAttribute("book") BookDto dto,
                        RedirectAttributes redirectAttributes) {

                Book book;

                if (dto.getId() != null) {

                        book = bookService.getBookById(dto.getId());
                        int quantityDifference = dto.getQuantity() - book.getQuantity();
                        if (book.getAvailableQuantity() + quantityDifference < 0) {

                                redirectAttributes.addFlashAttribute(
                                                "error",
                                                "Cannot reduce quantity because some books are borrowed.");

                                return "redirect:/books/edit/" + dto.getId();
                        }
                        book.setQuantity(dto.getQuantity());

                        book.setAvailableQuantity(
                                        book.getAvailableQuantity() + quantityDifference);

                } else {

                        book = new Book();

                }

                book.setTitle(dto.getTitle());

                book.setIsbn(dto.getIsbn());

                book.setPublishYear(dto.getPublishYear());

                book.setQuantity(dto.getQuantity());

                book.setAuthor(
                                authorService.getAuthorById(dto.getAuthorId()));

                book.setPublisher(
                                publisherService.getPublisherById(dto.getPublisherId()));

                book.setCategory(
                                categoryService.getCategoryById(dto.getCategoryId()));

                bookService.saveBook(book);
                redirectAttributes.addFlashAttribute(
                                "success",
                                "Book saved successfully.");
                if (returnTo != null && !returnTo.isBlank()) {

                        return "redirect:" + returnTo;

                }
                return "redirect:/books";
        }

        @GetMapping("/edit/{id}")
        public String editBook(@PathVariable Long id, Model model) {

                Book book = bookService.getBookById(id);

                BookDto dto = convertToDto(book);

                model.addAttribute("book", dto);

                model.addAttribute("authors",
                                authorService.getAllAuthors());

                model.addAttribute("publishers",
                                publisherService.getAllPublishers());

                model.addAttribute("categories",
                                categoryService.getAllCategories());

                return "book-form";
        }

        @GetMapping("/delete/{id}")
        public String deleteBook(@PathVariable Long id,
                        RedirectAttributes redirectAttributes) {

                bookService.deleteBook(id);
                redirectAttributes.addFlashAttribute(
                                "success",
                                "Book deleted successfully.");
                return "redirect:/books";
        }
}
