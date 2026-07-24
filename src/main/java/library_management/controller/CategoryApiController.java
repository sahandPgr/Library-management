package library_management.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library_management.entity.Category;
import library_management.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {


    private final CategoryService categoryService;


    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @PostMapping
    public Category createCategory(
            @RequestBody Category category) {


        return categoryService.saveCategory(category);

    }

}