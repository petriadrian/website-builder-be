package com.service;

import com.models.User;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public User findOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User " + email + " is not registered"));
    }

    public List<User> save(List<User> usersToBeSaved) {
        usersToBeSaved.forEach(userToBeSaved -> {
            userRepository.findByEmail(userToBeSaved.getEmail()).ifPresent(user -> userToBeSaved.setId(user.getId()));
        });
        return userRepository.saveAll(usersToBeSaved);
    }
}
