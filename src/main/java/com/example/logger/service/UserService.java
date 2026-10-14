package com.example.logger.service;

import com.example.logger.dto.UserRequestDTO;
import com.example.logger.entities.User;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final ConcurrentHashMap<Long, User> users =
            new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(1);

    public User createUser(UserRequestDTO request) {

        Long id = counter.getAndIncrement();

        User user = new User(
                id,
                request.getName(),
                request.getEmail()
        );

        users.put(id, user);

        AppLogger.getInstance().log(
                LogLevel.INFO,
                "User " + user.getName()
                        + " registered with ID " + id
        );

        return user;
    }

    public List<User> listUsers() {
        return List.copyOf(users.values());
    }

    public User serchById(Long id) {

        User user = users.get(id);

        if (user == null) {

            AppLogger.getInstance().log(
                    LogLevel.ERROR,
                    "User ID " + id + " not found."
            );

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found."
            );
        }

        return user;
    }

    public User searchByEmail(String email) {

        return users.values()
                .stream()
                .filter(user ->
                        user.getEmail()
                                .equalsIgnoreCase(email)
                )
                .findFirst()
                .orElse(null);
    }
}