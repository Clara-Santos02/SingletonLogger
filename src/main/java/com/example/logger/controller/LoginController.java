package com.example.logger.controller;

import com.example.logger.dto.LoginRequestDTO;
import com.example.logger.entities.User;
import com.example.logger.service.UserService;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String login(@RequestBody LoginRequestDTO request) {

        User user = userService.searchByEmail(request.getEmail());

        if (user == null) {
            AppLogger.getInstance().log(
                    LogLevel.WARNING,
                    "Login attempt with a non-existent email "
                            + request.getEmail()
            );

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User not found."
            );
        }

        AppLogger.getInstance().log(
                LogLevel.LOGIN,
                "User "
                        + user.getName()
                        + " logged in."
        );

        return "Login successful.";
    }
}