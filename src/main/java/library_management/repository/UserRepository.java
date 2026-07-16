package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.User;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}