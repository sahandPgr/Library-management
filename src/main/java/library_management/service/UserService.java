package library_management.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import library_management.entity.User;
import library_management.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {

        return repository.findAll();

    }

    public User getUserById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    public void save(User user) {
        if (repository.existsByEmail(user.getEmail().trim())) {
            throw new IllegalArgumentException(
                    "Email already exists.");
        }
        if (user.getPassword() != null &&
                !user.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(user.getPassword()));
        }
        user.setEmail(user.getEmail().trim());
        repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public long countUsers() {

        return repository.count();

    }

}