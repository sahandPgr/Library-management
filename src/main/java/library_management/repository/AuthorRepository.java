package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByFullNameIgnoreCase(String fullName);
    boolean existsByFullNameIgnoreCaseAndIdNot(String fullName, Long id);
}
