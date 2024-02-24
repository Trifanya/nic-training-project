package dev.trifanya.server_app.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    NOT_STARTED("Не начата"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Выполнена");

    public final String ruString;

    TaskStatus(String ruString) {
        this.ruString = ruString;
    }
}
