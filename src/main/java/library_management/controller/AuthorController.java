package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import library_management.entity.Author;
import library_management.service.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String authors(Model model) {

        model.addAttribute(
                "authors",
                authorService.getAllAuthors());

        return "authors";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute(
                "author",
                new Author());

        return "author-form";
    }

    @PostMapping("/save")
    public String saveAuthor(
            @ModelAttribute("author") Author author,
            RedirectAttributes redirectAttributes) {

        boolean isNew = author.getId() == null;

        try {

            authorService.saveAuthor(author);

            if (isNew) {

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Author created successfully.");

            } else {

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Author updated successfully.");

            }

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Failed to save author.");
        }

        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "author",
                authorService.getAuthorById(id));

        return "author-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            authorService.deleteAuthor(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Author deleted successfully.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Cannot delete author because it is used by one or more books.");

        }

        return "redirect:/authors";
    }

}