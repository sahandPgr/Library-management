package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import library_management.service.CategoryService;

@Controller
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public String categories(Model model) {

        model.addAttribute("categories", service.getAllCategories());

        return "categories";
    }

}