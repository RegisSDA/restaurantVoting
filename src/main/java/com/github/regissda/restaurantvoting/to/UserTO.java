package com.github.regissda.restaurantvoting.to;

import com.github.regissda.restaurantvoting.model.Role;

import java.util.Set;

/**
 * Created by MSI on 23.09.2017.
 */
public class UserTO {

    private String login;
    private String password;
    private Set<Role> roles;

    public UserTO() {
    }

    public UserTO(String login, String password, Set<Role> roles) {

        this.login = login;
        this.password = password;
        this.roles = roles;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
