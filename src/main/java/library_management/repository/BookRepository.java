package library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Book;
public interface BookRepository extends JpaRepository<Book, Long> {
List<Book> findByAvailableQuantityGreaterThan(int quantity);
}