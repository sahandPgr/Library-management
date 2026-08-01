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

import library_management.entity.Category;
import library_management.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String categories(Model model) {

        model.addAttribute("categories", service.getAllCategories());

        return "categories";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam(required = false, defaultValue = "/categories") String returnTo,
            Model model) {

        model.addAttribute("category", new Category());
        model.addAttribute("returnTo", returnTo);
        return "category-form";
    }

    @PostMapping("/save")
    public String saveCategory(
            @ModelAttribute("category") Category category,
            @RequestParam(required = false) String returnTo,
            RedirectAttributes redirectAttributes, Model model) {

        boolean isNew = category.getId() == null;

        try {

            service.saveCategory(category);

            if (isNew) {

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Category created successfully.");

            } else {

                redirectAttributes.addFlashAttribute(
                        "success",
                        "Category updated successfully.");

            }

        if (returnTo != null && !returnTo.isBlank()) {
            return "redirect:" + returnTo;
        }

        return "redirect:/categories";

        } catch (IllegalArgumentException e) {

        model.addAttribute(
                "error",
                e.getMessage()
        );

        model.addAttribute(
                "returnTo",
                returnTo
        );

        return "category-form";
    }
        
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            @RequestParam(required = false) String returnTo,
            Model model) {

        Category category = service.getCategoryById(id);

        model.addAttribute("category", category);
        model.addAttribute("returnTo", returnTo);

        return "category-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            service.deleteCategory(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Category deleted successfully.");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Cannot delete category because it is used by one or more books.");

        }

        return "redirect:/categories";
    }
}