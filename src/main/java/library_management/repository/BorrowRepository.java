package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}