package com.github.regissda.restaurantvoting.model;


import org.springframework.security.core.GrantedAuthority;

/**
 * Created by MSI on 13.09.2017.
 */

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
