package library_management.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library_management.dto.BorrowDto;
import library_management.entity.Book;
import library_management.entity.Borrow;
import library_management.entity.BorrowStatus;
import library_management.service.BookService;
import library_management.service.BorrowService;
import library_management.service.UserService;

@Controller
@RequestMapping("/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    private final UserService userService;

    private final BookService bookService;

    public BorrowController(
            BorrowService borrowService,
            UserService userService,
            BookService bookService) {

        this.borrowService = borrowService;
        this.userService = userService;
        this.bookService = bookService;

    }

    private BorrowDto convertToDto(Borrow borrow) {

        BorrowDto dto = new BorrowDto();

        dto.setId(borrow.getId());

        dto.setUserId(
                borrow.getUser().getId());

        dto.setBookId(
                borrow.getBook().getId());

        dto.setBorrowDate(
                borrow.getBorrowDate());

        dto.setDueDate(
                borrow.getDueDate());

        dto.setStatus(
                borrow.getStatus().name());
        return dto;
    }

    @GetMapping
    public String borrows(Model model) {

        model.addAttribute(
                "borrows",
                borrowService.getAllBorrows());

        return "borrows";
    }

    @GetMapping("/create")
    public String createBorrow(Model model) {

        model.addAttribute(
                "borrow",
                new BorrowDto());

        model.addAttribute(
                "users",
                userService.getAllUsers());

        model.addAttribute(
                "books",
                bookService.getAvailableBooks());
           model.addAttribute(
            "editMode",
            false
    );
        model.addAttribute(
        "returned",
        false
);
        return "borrow-form";

    }

    @PostMapping("/save")
    public String saveBorrow(
            @ModelAttribute("borrow") BorrowDto dto,
            RedirectAttributes redirectAttributes,
            Model model) {

        try {

            Borrow borrow;

            boolean isNew = dto.getId() == null;

            if (isNew) {

                borrow = new Borrow();

                Book book = bookService.getBookById(dto.getBookId());

                if (book.getAvailableQuantity() <= 0) {

                    model.addAttribute(
                            "error",
                            "This book is not available.");

                    model.addAttribute(
                            "borrow",
                            dto);

                    model.addAttribute(
                            "users",
                            userService.getAllUsers());

                    model.addAttribute(
                            "books",
                            bookService.getAvailableBooks());

                    return "borrow-form";
                }
                model.addAttribute(
        "editMode",
        false
);
model.addAttribute(
        "returned",
        false
);

                book.setAvailableQuantity(
                        book.getAvailableQuantity() - 1);

                bookService.saveBook(book);

                borrow.setBook(book);

                borrow.setUser(
                        userService.getUserById(dto.getUserId()));

            } else {

                borrow = borrowService.getBorrowById(dto.getId());

            }

            borrow.setBorrowDate(
                    dto.getBorrowDate());

            borrow.setDueDate(
                    dto.getDueDate());

            if (dto.getStatus() != null
                    && !dto.getStatus().isBlank()) {

                borrow.setStatus(
                        BorrowStatus.valueOf(dto.getStatus()));

            } else if (borrow.getStatus() == null) {

                borrow.setStatus(
                        BorrowStatus.BORROWED);
            }

            borrowService.saveBorrow(borrow);

            if (isNew) {

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Borrow created successfully.");

            } else {

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Borrow updated successfully.");
            }

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Failed to save borrow.");

        }

        return "redirect:/borrows";
    }

    @GetMapping("/edit/{id}")
    public String editBorrow(
            @PathVariable Long id,
            Model model) {

        Borrow borrow = borrowService.getBorrowById(id);
        BorrowDto dto = convertToDto(borrow);

        model.addAttribute(
                "borrow",
                dto);

        model.addAttribute(
                "users",
                userService.getAllUsers());
        model.addAttribute(
            "editMode",
            true
    );
    model.addAttribute(
            "returned",
            borrow.getStatus() == BorrowStatus.RETURNED
    );

        model.addAttribute(
                "books",
                bookService.getAvailableBooks());

        return "borrow-form";
    }

    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            Borrow borrow = borrowService.getBorrowById(id);

            if (borrow.getStatus() == BorrowStatus.RETURNED) {

                redirectAttributes.addFlashAttribute(
                        "warning",
                        "This book has already been returned.");

                return "redirect:/borrows";
            }

            Book book = borrow.getBook();

            book.setAvailableQuantity(
                    book.getAvailableQuantity() + 1);

            bookService.saveBook(book);

            borrow.setStatus(BorrowStatus.RETURNED);

            borrow.setReturnDate(LocalDate.now());

            borrowService.saveBorrow(borrow);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Book returned successfully.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Failed to return book.");

        }

        return "redirect:/borrows";
    }

}