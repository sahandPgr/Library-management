package library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import library_management.entity.Borrow;
import library_management.entity.BorrowStatus;
import library_management.repository.BorrowRepository;

@Service
public class BorrowService {

    private final BorrowRepository repository;

    public BorrowService(BorrowRepository repository) {
        this.repository = repository;
    }

    public List<Borrow> getAllBorrows() {

        return repository.findAll();

    }

    public void saveBorrow(Borrow borrow) {
        boolean duplicate;
        if (borrow.getId() == null) {

            duplicate = repository.existsByUserIdAndBookIdAndStatus(
                    borrow.getUser().getId(),
                    borrow.getBook().getId(),
                    BorrowStatus.BORROWED);

        } else {

            duplicate = repository.existsByUserIdAndBookIdAndStatusAndIdNot(
                    borrow.getUser().getId(),
                    borrow.getBook().getId(),
                    BorrowStatus.BORROWED,
                    borrow.getId());
        }

        if (duplicate) {

            throw new IllegalArgumentException(
                    "This user already has this book borrowed.");
        }

        repository.save(borrow);

    }

    public Borrow getBorrowById(Long id) {

        return repository.findById(id).orElse(null);

    }

    public void deleteBorrow(Long id) {

        repository.deleteById(id);

    }

    public long countActiveBorrows() {

        return repository
                .countByStatus(BorrowStatus.BORROWED);

    }

}