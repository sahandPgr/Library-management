package library_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import library_management.entity.User;
import library_management.repository.UserRepository;

@Service
public class UserService {


    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> getAllUsers(){

        return repository.findAll();

    }


    public User getUserById(Long id){

        return repository.findById(id)
                .orElse(null);

    }

}