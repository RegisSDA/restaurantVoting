package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by MSI on 13.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))//emptyScript
public class UsersDAOTest {

    @Autowired
    private UsersDAO usersDAO;

    @Test
    public void getTest() {
        User expected = new User();
        expected.setLogin("testuser1");
        expected.setPassword("testuser1");
        System.out.println("reults: "+usersDAO.getUserByLogin(expected.getLogin()).getLogin());

        assertEquals(expected.getPassword(),usersDAO.getUserByLogin(expected.getLogin()).getPassword());
    }
    @Test
    public void findTest() {
        User expected = new User();
        expected.setLogin("testuser2");
        expected.setPassword("testuser2");

        System.out.println("reults: "+usersDAO.findOne(expected.getLogin()).getLogin());

        assertEquals(expected.getPassword(),usersDAO.findOne(expected.getLogin()).getPassword());
    }

    @Test
    public void saveTest(){
        User user = new User();
        user.setLogin("savetest");
        user.setPassword("savepassword");

        usersDAO.save(user);
        User getedUser = usersDAO.findOne(user.getLogin());
        System.out.println(user.getLogin()+" : "+user.getPassword());
        System.out.println(getedUser.getLogin()+" : "+getedUser.getPassword());
    }


}