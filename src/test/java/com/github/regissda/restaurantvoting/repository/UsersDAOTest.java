package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.matcher.BeanMatcher;
import com.github.regissda.restaurantvoting.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static com.github.regissda.restaurantvoting.repository.TestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by MSI on 21.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class UsersDAOTest {

    private BeanMatcher matcher = BeanMatcher.of(User.class,(expected, actual) -> (
            expected.getLogin().equals(actual.getLogin())
            && expected.getPassword().equals(actual.getPassword())
            && expected.getRoles().equals(actual.getRoles())
            ));

    @Autowired
    private UsersDAO usersDAO;

    @Test
    public void testGet(){
        matcher.assertEquals(USER_1,usersDAO.findOne(USER_1.getLogin()));
    }

    @Test
    public void testSave(){
        User user = getCreatedUser();
        usersDAO.save(user);
        matcher.assertEquals(Arrays.asList(USER_1,USER_2,USER_3,user),usersDAO.findAll());
    }

    @Test
    public void testFindAll(){
        matcher.assertEquals(Arrays.asList(USER_1,USER_2,USER_3),usersDAO.findAll());
    }
    @Test
    public void testDelete(){
        usersDAO.delete(USER_1.getLogin());
        matcher.assertEquals(Arrays.asList(USER_2,USER_3),usersDAO.findAll());
    }

    @Test
    public void testUpdate(){
        User updated = getUpdatedUser(USER_1);
        usersDAO.save(updated);
        matcher.assertEquals(Arrays.asList(USER_2,USER_3,updated),usersDAO.findAll());
    }

}