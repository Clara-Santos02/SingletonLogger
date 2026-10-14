package com.example.logger.service;

import com.example.logger.dto.UserRequestDTO;
import com.example.logger.entities.User;
import com.example.logger.repository.UserRepository;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDTO request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user = userRepository.save(user);

        AppLogger.getInstance().log(
                LogLevel.INFO,
                "User "
                        + user.getName()
                        + " registered with ID "
                        + user.getId()
        );

        return user;
    }

    public List<User> listUsers() {

        return userRepository.findAll();
    }

    public User searchById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() -> {

                    AppLogger.getInstance().log(
                            LogLevel.ERROR,
                            "User with ID "
                                    + id
                                    + " not found."
                    );

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "User not found"
                    );
                });
    }

    public User searchByEmail(String email) {

        return userRepository
                .findByEmail(email)
                .orElse(null);
    }
}