package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}