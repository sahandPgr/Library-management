package library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library_management.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}