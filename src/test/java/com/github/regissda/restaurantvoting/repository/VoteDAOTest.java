package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.model.Vote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by MSI on 13.09.2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteDAOTest {

    @Autowired
    private VotesDAO voteDAO;

    @Test
    public void getTest(){
        Vote vote = voteDAO.findOne(1);
        Restaurant rest;
        if (vote!=null) {
            rest = vote.getRestaurant();
            System.out.println(rest.getName());
        }

    }
}