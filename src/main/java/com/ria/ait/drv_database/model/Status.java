package com.ria.ait.drv_database.model;

public enum Status {
    PREMIUM (2),
    BASIC (1),
    SUSPENDED (0);

    String name;
    Status(int i) {
        this.name = String.valueOf(i);
    }
}
