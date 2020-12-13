package com.example.backend.model.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("USER", "Regular user"),
    TEMP_USER("TEMP_USER", "Temporary user"),
    MANAGER("MANAGER", "Project Manager"),
    ADMIN("ADMIN", "Super Admin");
    private final String name;
    private final String description;

    RoleType(java.lang.String name, java.lang.String description) {
        this.name = name;
        this.description = description;
    }
}
