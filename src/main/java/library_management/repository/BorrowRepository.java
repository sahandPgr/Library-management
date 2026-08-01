package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.Borrow;
import library_management.entity.BorrowStatus;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    long countByStatus(BorrowStatus status);
    boolean existsByUserIdAndBookIdAndStatus(
        Long userId,
        Long bookId,
        BorrowStatus status
);
boolean existsByUserIdAndBookIdAndStatusAndIdNot(
        Long userId,
        Long bookId,
        BorrowStatus status,
        Long id
);
}