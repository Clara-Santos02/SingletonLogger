package com.example.logger.singleton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppLogger {

    private static AppLogger instance;

    private final List<String> logs = new ArrayList<>();

    private AppLogger() {
        System.out.println("=== APP LOGGER CREATED ===");
    }

    public static synchronized AppLogger getInstance() {

        if (instance == null) {
            instance = new AppLogger();
        }

        return instance;
    }

    public void log(String type, String message) {

        String log = "[" + LocalDateTime.now() + "]"
                + " [" + type + "] "
                + message;

        logs.add(log);

        System.out.println(log);
    }

    public List<String> getLogs() {
        return logs;
    }

    public void cleanLogs() {
        logs.clear();
    }
}