package library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import library_management.entity.Category;
import library_management.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

}