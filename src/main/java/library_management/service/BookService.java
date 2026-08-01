package library_management.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import library_management.entity.Book;
import library_management.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAvailableBooks() {
        return repository.findByAvailableQuantityGreaterThan(0);
    }

    public void saveBook(Book book) {
        if (book.getIsbn() != null &&
                !book.getIsbn().trim().isEmpty()) {

            String isbn = book.getIsbn().trim();

            boolean duplicate;

            if (book.getId() == null) {

                duplicate = repository.existsByIsbn(isbn);

            } else {

                duplicate = repository.existsByIsbnAndIdNot(
                        isbn,
                        book.getId());
            }

            if (duplicate) {
                throw new IllegalArgumentException(
                        "A book with this ISBN already exists.");
            }

            book.setIsbn(isbn);

        } else {
            book.setIsbn(generateIsbn());
        }

        if (book.getId() == null) {

            book.setAvailableQuantity(
                    book.getQuantity());
        } else {

            Book oldBook = repository.findById(book.getId())
                    .orElseThrow();

            int borrowedCount = oldBook.getQuantity()
                    - oldBook.getAvailableQuantity();

            if (book.getQuantity() < borrowedCount) {

                throw new IllegalArgumentException(
                        "Quantity cannot be less than borrowed books.");
            }

            book.setAvailableQuantity(
                    book.getQuantity() - borrowedCount);
        }
        repository.save(book);
    }

    public Book getBookById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    public long countBooks() {

        return repository.count();

    }

    private String generateIsbn() {

        String isbn;

        do {

            isbn = String.valueOf(
                    100000 +
                            new Random().nextInt(900000));

        } while (repository.existsByIsbn(isbn));

        return isbn;
    }
}