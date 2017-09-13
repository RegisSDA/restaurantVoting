package com.github.regissda.restaurantvoting.model;

import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.util.EnumSet;

/**
 * Created by MSI on 13.09.2017.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column
    private String login;
    @Column
    private String password;
 //   private EnumSet<Role> roles;

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

   /* public EnumSet<Role> getRoles() {
        return roles;
    }

    public void setRoles(EnumSet<Role> roles) {
        this.roles = roles;
    }*/
}
