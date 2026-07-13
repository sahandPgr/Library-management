package library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import library_management.entity.Publisher;
import library_management.repository.PublisherRepository;

@Service
public class PublisherService {

    private final PublisherRepository repository;

    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }

    public List<Publisher> getAllPublishers() {
        return repository.findAll();
    }

    public void savePublisher(Publisher publisher) {
        repository.save(publisher);
    }

    public Publisher getPublisherById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletePublisher(Long id) {
        repository.deleteById(id);
    }
}