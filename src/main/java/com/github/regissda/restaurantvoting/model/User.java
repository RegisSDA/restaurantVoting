package com.github.regissda.restaurantvoting.model;

import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.Set;

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
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

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
}
