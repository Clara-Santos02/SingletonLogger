package com.example.logger.controller;

import com.example.logger.dto.UserRequestDTO;
import com.example.logger.entities.User;
import com.example.logger.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }
}
