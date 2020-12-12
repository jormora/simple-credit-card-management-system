package com.example.bank.users.service;

import com.example.bank.users.model.User;
import com.example.bank.users.repository.UserRepository;
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

    public void deleteByUsername(String id) {
        this.userRepository.deleteById(id);
    }

}
