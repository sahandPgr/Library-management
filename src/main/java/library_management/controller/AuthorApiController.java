package library_management.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library_management.entity.Author;
import library_management.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorApiController {


private final AuthorService authorService;


public AuthorApiController(
AuthorService authorService){

this.authorService = authorService;

}



@PostMapping
public Author createAuthor(
@RequestBody Author author){


return authorService.saveAuthor(author);


}


}