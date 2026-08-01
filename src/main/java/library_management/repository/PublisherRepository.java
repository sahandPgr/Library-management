package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    boolean existsByNameIgnoreCase(String name);
}