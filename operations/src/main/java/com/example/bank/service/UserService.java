package com.example.bank.service;

import com.example.bank.model.User;
import com.example.bank.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    public void deleteById(String id) {
        this.userRepository.deleteById(id);
    }

}
