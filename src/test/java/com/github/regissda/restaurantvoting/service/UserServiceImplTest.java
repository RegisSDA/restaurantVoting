package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.exceptions.AccessDenied;
import com.github.regissda.restaurantvoting.matcher.BeanMatcher;
import com.github.regissda.restaurantvoting.model.User;
import com.github.regissda.restaurantvoting.to.UserTO;
import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.github.regissda.restaurantvoting.repository.TestUtil.USER_1;
import static com.github.regissda.restaurantvoting.service.ServiceTestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by MSI on 24.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private BeanMatcher matcher = BeanMatcher.of(UserTO.class, (expected, actual) -> (
            expected.getLogin().equals(actual.getLogin())
                    && expected.getPassword().equals(actual.getPassword())
                    && expected.getRoles().equals(actual.getRoles()))
    );


    @Test
    public void getAll() throws Exception {
        matcher.assertEquals(Arrays.asList(USER_TO_1, USER_TO_2, USER_TO_3), userService.getAll());
    }

    @Test
    public void get() throws Exception {
        matcher.assertEquals(USER_TO_1, userService.get(USER_TO_1.getLogin()));
    }

    @Test
    public void create() throws Exception {
        UserTO userTO = createUserTO();
        userService.create(userTO);
        matcher.assertEquals(Arrays.asList(USER_TO_1, USER_TO_2, USER_TO_3, userTO), userService.getAll());

    }

    @Test
    public void update() throws Exception {
        UserTO updated = updatedUserTO(USER_TO_3);
        userService.update(updated);
        matcher.assertEquals(Arrays.asList(USER_TO_1, USER_TO_2, updated), userService.getAll());
    }

    @Test(expected = AccessDenied.class)
    public void updateAccessDenied() throws Exception {
        UserTO updated = updatedUserTO(USER_TO_1);
        userService.update(updated);
    }

    @Test
    public void selfUpdate() throws Exception {
        UserTO updated = updatedUserTO(USER_TO_1);
        userService.selfUpdate(updated);
        matcher.assertEquals(Arrays.asList(USER_TO_2, USER_TO_3, updated), userService.getAll());
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER_TO_3.getLogin());
        matcher.assertEquals(Arrays.asList(USER_TO_1, USER_TO_2), userService.getAll());
    }

    @Test(expected = AccessDenied.class)
    public void deleteAccessDenied() throws Exception {
        userService.delete(USER_TO_2.getLogin());
    }

    @Test
    public void selfDelete() throws Exception {
        userService.selfDelete(USER_TO_1.getLogin());
        matcher.assertEquals(Arrays.asList(USER_TO_2, USER_TO_3), userService.getAll());
    }

}