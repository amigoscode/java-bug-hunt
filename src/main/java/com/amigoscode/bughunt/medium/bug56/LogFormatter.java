package com.amigoscode.bughunt.medium.bug56;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFormatter {

    public enum Level {
        DEBUG, INFO, WARN, ERROR, FATAL
    }

    public static class LogEntry {
        private final Level level;
        private final String message;
        private final String source;
        private final LocalDateTime timestamp;

        public LogEntry(Level level, String message, String source, LocalDateTime timestamp) {
            this.level = level;
            this.message = message;
            this.source = source;
            this.timestamp = timestamp;
        }

        public Level getLevel() {
            return level;
        }

        public String getMessage() {
            return message;
        }

        public String getSource() {
            return source;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<LogEntry> entries = new ArrayList<>();

    public void addEntry(LogEntry entry) {
        entries.add(entry);
    }

    public String formatEntry(LogEntry entry) {
        String ts = entry.getTimestamp().format(FORMATTER);
        String lvl = entry.getLevel().name();
        String src = entry.getSource().toString();
        String msg = entry.getMessage().toString();
        return String.format("[%s] %s %s — %s", ts, lvl, src, msg);
    }

    public String formatAll() {
        return entries.stream()
                .map(this::formatEntry)
                .collect(Collectors.joining("\n"));
    }

    public List<LogEntry> filterByLevel(Level minLevel) {
        return entries.stream()
                .filter(e -> e.getLevel().ordinal() >= minLevel.ordinal())
                .collect(Collectors.toList());
    }

    public long countByLevel(Level level) {
        return entries.stream()
                .filter(e -> e.getLevel() == level)
                .count();
    }

    public String summary() {
        StringBuilder sb = new StringBuilder("Log Summary:\n");
        sb.append("Total entries: ").append(entries.size()).append("\n");
        for (Level level : Level.values()) {
            long count = countByLevel(level);
            if (count > 0) {
                sb.append("  ").append(level.name()).append(": ").append(count).append("\n");
            }
        }
        return sb.toString();
    }

    public int size() {
        return entries.size();
    }

    public LogEntry latest() {
        if (entries.isEmpty()) {
            throw new IllegalStateException("No log entries");
        }
        return entries.get(entries.size() - 1);
    }
}
