package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.matcher.BeanMatcher;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.to.VoteTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

import static com.github.regissda.restaurantvoting.repository.TestUtil.USER_1;
import static com.github.regissda.restaurantvoting.repository.TestUtil.USER_2;
import static com.github.regissda.restaurantvoting.repository.TestUtil.USER_3;
import static com.github.regissda.restaurantvoting.service.ServiceTestUtil.*;

import static org.junit.Assert.*;

/**
 * Created by MSI on 24.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VotesServiceImplTest {

    @Autowired
    private VotesService votesService;

    private BeanMatcher matcher = BeanMatcher.of(VoteTO.class);

    @Test
    public void getVotesOfUser() throws Exception {
        matcher.assertEquals(Arrays.asList(VOTE_TO_1, VOTE_TO_4), votesService.getVotesOfUser(USER_1.getLogin()));
        matcher.assertEquals(Arrays.asList(VOTE_TO_2, VOTE_TO_5), votesService.getVotesOfUser(USER_2.getLogin()));
        matcher.assertEquals(Arrays.asList(VOTE_TO_3, VOTE_TO_6), votesService.getVotesOfUser(USER_3.getLogin()));
    }

    @Test
    public void getVote() throws Exception {
        matcher.assertEquals(VOTE_TO_1, votesService.getVote(USER_1.getLogin(), LocalDate.parse(VOTE_TO_1.getDate())));
        matcher.assertEquals(VOTE_TO_5, votesService.getVote(USER_2.getLogin(), LocalDate.parse(VOTE_TO_5.getDate())));
    }

    @Test
    public void create() throws Exception {
        VoteTO voteTO = createVoteTO();
        Vote creted = votesService.create(voteTO, USER_1.getLogin());
        voteTO.setId(creted.getId());
        matcher.assertEquals(Arrays.asList(VOTE_TO_1, VOTE_TO_4, voteTO), votesService.getVotesOfUser(USER_1.getLogin()));
    }

    @Test
    public void update() throws Exception {
        VoteTO updated = updateVoteTO(VOTE_TO_2);
        votesService.update(updated, USER_2.getLogin());
        matcher.assertEquals(Arrays.asList(updated, VOTE_TO_5), votesService.getVotesOfUser(USER_2.getLogin()));
    }

    @Test
    public void delete() throws Exception {
        VoteTO voteTO = createVoteTO();
        Vote created = votesService.create(voteTO, USER_1.getLogin());
        voteTO.setId(created.getId());
        matcher.assertEquals(voteTO, votesService.getVote(USER_1.getLogin(), LocalDate.now()));
        votesService.delete(voteTO.getId(), USER_1.getLogin());
        matcher.assertEquals(Arrays.asList(VOTE_TO_1, VOTE_TO_4), votesService.getVotesOfUser(USER_1.getLogin()));
    }

    @Test
    public void getRestaurantsWithVotes() throws Exception {
        matcher.assertEquals(Arrays.asList(RESTAURANT_WITH_VOTES_2, RESTAURANT_WITH_VOTES_3),
                votesService.getRestaurantsWithVotes(LocalDate.of(2017, 9, 11)));
    }

}