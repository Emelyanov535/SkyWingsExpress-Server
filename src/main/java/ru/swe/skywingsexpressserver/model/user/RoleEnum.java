package ru.swe.skywingsexpressserver.model.user;

public enum RoleEnum {
    USER("USER"),
    OPERATOR("OPERATOR"),
    MODERATOR("MODERATOR");

    private static final String ROLE_PREFIX = "ROLE_";
    private final String role;


    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return ROLE_PREFIX + role;
    }
}
