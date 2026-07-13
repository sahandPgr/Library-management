package library_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import library_management.entity.Category;
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

    @GetMapping("/categories/new")
    public String showCreateForm(Model model) {

        model.addAttribute("category", new Category());

        return "category-form";
    }

    @PostMapping("/categories")
public String saveCategory(@ModelAttribute("category") Category category) {

    service.saveCategory(category);

    return "redirect:/categories";
}

    @GetMapping("/categories/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {

    Category category = service.getCategoryById(id);

    model.addAttribute("category", category);

    return "category-form";
}

    @GetMapping("/categories/delete/{id}")
public String deleteCategory(@PathVariable Long id) {

    service.deleteCategory(id);

    return "redirect:/categories";
}
}
