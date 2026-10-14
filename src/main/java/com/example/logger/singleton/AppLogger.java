package com.example.logger.singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppLogger {

    private static AppLogger instance;

    private final List<LogEntry> logs = new ArrayList<>();

    private AppLogger() {
        System.out.println(
                "===== APP LOGGER SINGLETON CREATED ====="
        );
    }

    public static synchronized AppLogger getInstance() {

        if (instance == null) {
            instance = new AppLogger();
        }

        return instance;
    }

    public void log(LogLevel level, String message) {

        LogEntry log = new LogEntry(
                LocalDateTime.now(),
                level,
                message,
                Thread.currentThread().getName()
        );

        logs.add(log);

        System.out.println(
                "[" + log.getDateTime() + "] "
                        + "[" + level + "] "
                        + "[" + log.getThread() + "] "
                        + message
        );
    }

    public List<LogEntry> getLogs() {return List.copyOf(logs);}

    public void cleanLogs() {
        logs.clear();
    }
}