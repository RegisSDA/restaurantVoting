package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.matcher.BeanMatcher;
import com.github.regissda.restaurantvoting.model.Vote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.github.regissda.restaurantvoting.repository.TestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by MSI on 18.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class VotesDAOTest {

    private BeanMatcher matcher = BeanMatcher.of(Vote.class,((expected, actual) -> (
            expected.getId().equals(actual.getId())
                    && expected.getUser().getLogin().equals(actual.getUser().getLogin())
                    && expected.getRestaurant().getName().equals(actual.getRestaurant().getName())
                    && expected.getVoteDate().equals(actual.getVoteDate())
            )));

    @Autowired
    private VotesDAO votesDAO;

    @Test
    public void testFindAllByUserEquals() throws Exception {
        List<Vote> votes = votesDAO.findAllByUserEquals(USER_1);
        matcher.assertEquals(Arrays.asList(VOTE_1,VOTE_4),votes);
    }

    @Test
    public void testGetByVoteDateAndUser() throws Exception {
        Vote vote = votesDAO.getByVoteDateAndUser(LocalDate.of(2017,9,10),USER_1);
        matcher.assertEquals(VOTE_1,vote);
    }

    @Test
    public void testFindAllByVoteDateEquals() throws Exception {
        List<Vote> votes = votesDAO.findAllByVoteDateEquals(LocalDate.of(2017,9,10));
        matcher.assertEquals(Arrays.asList(VOTE_1,VOTE_2,VOTE_3),votes);
    }

    @Test
    public void testSave(){
        Vote created = getCreatedVote();
        votesDAO.save(created);
        matcher.assertEquals(Arrays.asList(VOTE_1,VOTE_4,created),votesDAO.findAllByUserEquals(USER_1));
    }

    @Test
    public void testDelete(){
        Vote created = getCreatedVote();
        votesDAO.save(created);
        votesDAO.delete(created);
        matcher.assertEquals(votesDAO.findAllByUserEquals(USER_1), Arrays.asList(VOTE_1,VOTE_4));
    }

    @Test
    public void testNotFound(){
        Vote vote = votesDAO.getByVoteDateAndUser(LocalDate.of(2017,8,20),USER_1);
        assertTrue(vote==null);
    }

    @Test
    public void testUpdate(){
        Vote vote = getCreatedVote();
        votesDAO.save(vote);
        Vote updated = getUpdatedVote(vote);
        votesDAO.save(updated);
        matcher.assertEquals(updated,votesDAO.findOne(updated.getId()));
    }

}