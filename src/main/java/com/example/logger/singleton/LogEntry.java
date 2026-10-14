package com.example.logger.singleton;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LogEntry {
    private LocalDateTime dateTime;
    private LogLevel level;
    private String message;
    private String thread;
}
