package dev.trifanya.server_app.model;

import lombok.Getter;

@Getter
public enum TaskPriority {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private final String ruString;

    TaskPriority(String ruString) {
        this.ruString = ruString;
    }

    public static TaskPriority getTaskPriority(String str) {
        switch (str) {
            case "Средний":
                return MEDIUM;
            case "Высокий":
                return HIGH;
            default:
                return LOW;
        }
    }
}
