package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.model.User;
import com.github.regissda.restaurantvoting.to.UserTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by MSI on 23.09.2017.
 */
public interface UserService {

    List<UserTO> getAll();

    UserTO get(String login);

    User create(UserTO userTO);

    void update(UserTO userTO);

    void selfUpdate(UserTO userTO);

    void selfDelete(String login);

    void delete(String login);
}
