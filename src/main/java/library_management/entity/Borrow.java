package library_management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import library_management.entity.User;
@Entity
@Table(name = "borrows")
public class Borrow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


    @Column(name = "borrow_date")
    private LocalDate borrowDate;


    @Column(name = "due_date")
    private LocalDate dueDate;


    @Column(name = "return_date")
    private LocalDate returnDate;


    @Enumerated(EnumType.STRING)
    private BorrowStatus status;


    public Borrow() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Book getBook() {
        return book;
    }


    public void setBook(Book book) {
        this.book = book;
    }


    public LocalDate getBorrowDate() {
        return borrowDate;
    }


    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }


    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public LocalDate getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


    public BorrowStatus getStatus() {
        return status;
    }


    public void setStatus(BorrowStatus status) {
        this.status = status;
    }
}