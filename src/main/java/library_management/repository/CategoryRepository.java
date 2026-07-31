package library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByIdAsc();
    boolean existsByNameIgnoreCase(String name);
}