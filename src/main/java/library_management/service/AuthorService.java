package library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import library_management.entity.Author;
import library_management.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public Author saveAuthor(Author author) {
        return repository.save(author);
    }

    public Author getAuthorById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }
}