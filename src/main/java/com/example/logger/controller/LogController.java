package com.example.logger.controller;

import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogEntry;
import com.example.logger.singleton.LogLevel;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logs")
public class LogController {

    @GetMapping
    public List<LogEntry> listLogs(@RequestParam(required = false) LogLevel level) {

        List<LogEntry> logs =
                AppLogger
                        .getInstance()
                        .getLogs();

        if (level == null) {
            return logs;
        }

        return logs
                .stream()
                .filter(log ->
                        log.getLevel() == level
                )
                .toList();
    }

    @GetMapping("/statistics")
    public Map<String, Long> statistics() {

        List<LogEntry> logs =
                AppLogger
                        .getInstance()
                        .getLogs();

        Map<String, Long> result =
                new LinkedHashMap<>();

        result.put(
                "TOTAL",
                (long) logs.size()
        );

        for (LogLevel level
                : LogLevel.values()) {

            long quantity =
                    logs.stream()
                            .filter(log ->
                                    log.getLevel()
                                            == level
                            )
                            .count();

            result.put(
                    level.name(),
                    quantity
            );
        }

        return result;
    }

    @DeleteMapping
    public String clean() {

        AppLogger
                .getInstance()
                .cleanLogs();

        return "Logs deleted successfully";
    }
}