package library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import library_management.entity.Book;
import library_management.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public void saveBook(Book book) {
        repository.save(book);
    }

    public Book getBookById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }
}