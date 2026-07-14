package library_management.controller;


import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import library_management.dto.BorrowDto;
import library_management.entity.Book;
import library_management.entity.Borrow;
import library_management.entity.BorrowStatus;
import library_management.service.BookService;
import library_management.service.BorrowService;
import library_management.service.UserService;

@Controller
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



    @GetMapping("/borrows")
    public String borrows(Model model){

        model.addAttribute(
                "borrows",
                borrowService.getAllBorrows()
        );

        return "borrows";
    }



    @GetMapping("/borrows/new")
    public String createBorrow(Model model){


        model.addAttribute(
                "borrow",
                new BorrowDto()
        );


        model.addAttribute(
                "users",
                userService.getAllUsers()
        );


        model.addAttribute(
                "books",
                bookService.getAvailableBooks()
        );


        return "borrow-form";

    }



    @PostMapping("/borrows")
public String saveBorrow(
        @ModelAttribute("borrow") BorrowDto dto,
        Model model) {

    Borrow borrow;

    if (dto.getId() != null) {
        borrow = borrowService.getBorrowById(dto.getId());
    } else {
        borrow = new Borrow();
    }

    Book book = bookService.getBookById(dto.getBookId());

    if (book.getAvailableQuantity() <= 0) {

        model.addAttribute("error", "این کتاب در حال حاضر موجود نیست.");

        model.addAttribute("borrow", dto);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("books", bookService.getAvailableBooks());

        return "borrow-form";
    }

    book.setAvailableQuantity(book.getAvailableQuantity() - 1);
    bookService.saveBook(book);

    borrow.setBook(book);

    borrow.setUser(
            userService.getUserById(dto.getUserId())
    );

    borrow.setBorrowDate(dto.getBorrowDate());
    borrow.setDueDate(dto.getDueDate());
    borrow.setReturnDate(dto.getReturnDate());

    if (dto.getStatus() != null) {
        borrow.setStatus(BorrowStatus.valueOf(dto.getStatus()));
    } else {
        borrow.setStatus(BorrowStatus.BORROWED);
    }

    borrowService.saveBorrow(borrow);

    return "redirect:/borrows";
}

 @GetMapping("/borrows/return/{id}")
public String returnBook(@PathVariable Long id) {

    Borrow borrow = borrowService.getBorrowById(id);

    if (borrow.getStatus() == BorrowStatus.RETURNED) {
        return "redirect:/borrows";
    }

    Book book = borrow.getBook();

    book.setAvailableQuantity(
            book.getAvailableQuantity() + 1
    );

    bookService.saveBook(book);

    borrow.setStatus(BorrowStatus.RETURNED);

    borrow.setReturnDate(LocalDate.now());

    borrowService.saveBorrow(borrow);

    return "redirect:/borrows";
}



    @GetMapping("/borrows/delete/{id}")
    public String deleteBorrow(
            @PathVariable Long id){


         Borrow borrow = borrowService.getBorrowById(id);
          if (borrow.getStatus() == BorrowStatus.BORROWED) {

        Book book = borrow.getBook();

        book.setAvailableQuantity(book.getAvailableQuantity() + 1);

        bookService.saveBook(book);
    }

    borrowService.deleteBorrow(id);

        return "redirect:/borrows";

    }


}