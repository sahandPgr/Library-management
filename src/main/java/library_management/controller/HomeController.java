package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import library_management.service.BookService;
import library_management.service.BorrowService;
import library_management.service.UserService;
@Controller
public class HomeController {

    private final BookService bookService;
    private final UserService userService;
    private final BorrowService borrowService;


    public HomeController(
            BookService bookService,
            UserService userService,
            BorrowService borrowService) {

        this.bookService = bookService;
        this.userService = userService;
        this.borrowService = borrowService;
    }



    @GetMapping("/")
    public String dashboard(Model model){


        model.addAttribute(
                "bookCount",
                bookService.countBooks()
        );


        model.addAttribute(
                "userCount",
                userService.countUsers()
        );


        model.addAttribute(
                "borrowCount",
                borrowService.countActiveBorrows()
        );


        return "index";
    }
}