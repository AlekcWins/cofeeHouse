package ru.cofee.house.model.auth;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {

    USER,
    ADMIN;

    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + name();
    }

    public String getName() {
        return name();
    }
}
