package com.example.logger.controller;

import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogEntry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @GetMapping
    public List<LogEntry> listLogs() {

        AppLogger logger = AppLogger.getInstance();

        return logger.getLogs();
    }

    @DeleteMapping
    public String cleanLogs() {

        AppLogger logger = AppLogger.getInstance();

        logger.cleanLogs();

        return "Deleted logs successfully";
    }
}
