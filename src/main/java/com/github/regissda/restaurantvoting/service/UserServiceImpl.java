package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.exceptions.AccessDenied;
import com.github.regissda.restaurantvoting.exceptions.AlreadyExist;
import com.github.regissda.restaurantvoting.model.Role;
import com.github.regissda.restaurantvoting.model.User;
import com.github.regissda.restaurantvoting.repository.UsersDAO;
import com.github.regissda.restaurantvoting.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;

/**
 * Created by MSI on 22.09.2017.
 */

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UsersDAO usersDAO;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = usersDAO.findOne(login);

        return new UserDetail(user.getLogin(), user.getPassword(), true, true, true, true, user.getRoles());
    }

    @Override
    public List<UserTO> getAll() {
        return usersDAO.findAll().stream().map(a -> convert(a, UserTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserTO get(String login) {
        return convert(usersDAO.findOne(login), UserTO.class);
    }

    @Override
    public User create(UserTO userTO) {
        User target = usersDAO.findOne(userTO.getLogin());
        if (target != null) {
            throw new AlreadyExist();
        }
        return usersDAO.save(convert(userTO, User.class));
    }

    @Override
    public void update(UserTO userTO) {
        User target = usersDAO.findOne(userTO.getLogin());
        if (target.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDenied();
        }
        usersDAO.save(convert(userTO, User.class));
    }

    @Override
    public void selfUpdate(UserTO userTO) {
        usersDAO.save(convert(userTO, User.class));
    }

    @Override
    public void selfDelete(String login) {
        usersDAO.delete(login);
    }

    @Override
    public void delete(String login) {
        User target = usersDAO.findOne(login);
        if (target.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new AccessDenied();
        }
        usersDAO.delete(login);
    }

    private static class UserDetail extends org.springframework.security.core.userdetails.User {
        public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        }
    }
}
