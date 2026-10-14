package com.example.logger.controller;

import com.example.logger.dto.LoginRequestDTO;
import com.example.logger.singleton.AppLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request) {

        AppLogger logger = AppLogger.getInstance();

        logger.log(
                "LOGIN",
                "User " + request.getUser()
                        + " logged in successfully."
        );

        return ResponseEntity.ok(
                "Login successful"
        );
    }
}
