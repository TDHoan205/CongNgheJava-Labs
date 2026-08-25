package vn.edu.eaut.lab10.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple file logger for Lab 10.
 * Logs to the Tomcat logs directory.
 */
public class Logger {

    private static final String LOG_FILE = "lab10-secured-app.log";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Object LOCK = new Object();

    public enum Level {
        INFO, WARN, ERROR
    }

    public static void log(Level level, String message) {
        log(level, message, null);
    }

    public static void log(Level level, String message, Throwable throwable) {
        String timestamp = DATE_FORMAT.format(LocalDateTime.now());
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp).append("] ");
        sb.append("[").append(level.name()).append("] ");
        sb.append(message);

        if (throwable != null) {
            sb.append("\n  Exception: ").append(throwable.getMessage());
            if (throwable.getStackTrace() != null && throwable.getStackTrace().length > 0) {
                sb.append("\n  at ").append(throwable.getStackTrace()[0]);
            }
        }

        sb.append("\n");

        String catalinaBase = System.getProperty("catalina.base", System.getProperty("user.dir"));
        Path logDirectory = Path.of(catalinaBase, "logs");
        Path logFile = logDirectory.resolve(LOG_FILE);

        try {
            synchronized (LOCK) {
                Files.createDirectories(logDirectory);
                Files.writeString(logFile, sb.toString(), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void warn(String message) {
        log(Level.WARN, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }

    public static void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }
}
