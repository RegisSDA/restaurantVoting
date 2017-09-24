package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.exceptions.AlreadyExist;
import com.github.regissda.restaurantvoting.exceptions.NotFoundException;
import com.github.regissda.restaurantvoting.exceptions.VotingOverException;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import com.github.regissda.restaurantvoting.repository.UsersDAO;
import com.github.regissda.restaurantvoting.repository.VotesDAO;
import com.github.regissda.restaurantvoting.to.RestaurantWithVotes;
import com.github.regissda.restaurantvoting.to.VoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;
import static com.github.regissda.restaurantvoting.web.UserRestController.VOTING_STOP_TIME;

/**
 * Created by MSI on 23.09.2017.
 */
@Service
public class VotesServiceImpl implements VotesService {

    @Autowired
    private VotesDAO votesDAO;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Override
    public List<VoteTO> getVotesOfUser(String user) {
        return votesDAO.findAllByUserEquals(usersDAO.getOne(user))
                .stream()
                .map(a -> convert(a, VoteTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VoteTO getVote(String name, LocalDate date) {
        Vote vote = votesDAO.getByVoteDateAndUser(date, usersDAO.getOne(name));
        if (vote == null) {
            throw new NotFoundException();
        }
        return convert(vote, VoteTO.class);
    }

    @Override
    @CacheEvict(value = "top1", allEntries = true)
    @Transactional
    public Vote create(VoteTO voteTO, String userName) {
        if (voteTO.getId() != null) {
            throw new AlreadyExist();
        }
        Vote vote = new Vote(null, usersDAO.getOne(userName), restaurantDAO.getOne(voteTO.getRestaurant()), LocalDate.parse(voteTO.getDate()));
        return votesDAO.save(vote);
    }

    @Override
    @CacheEvict(value = "top1", allEntries = true)
    @Transactional
    public void update(VoteTO voteTO, String userName) {
        Vote oldVote = votesDAO.getOne(voteTO.getId());
        if (oldVote == null || !oldVote.getUser().getLogin().equals(userName)) {
            throw new NotFoundException();
        }
        oldVote.setRestaurant(restaurantDAO.getOne(voteTO.getRestaurant()));
        votesDAO.save(oldVote);
    }

    @Override
    @CacheEvict(value = "top1", allEntries = true)
    @Transactional
    public void delete(Integer id, String name) {
        Vote vote = votesDAO.getOne(id);
        if (LocalTime.now().isBefore(VOTING_STOP_TIME) && vote.getUser().getLogin().equals(name)) {
            votesDAO.delete(id);
        } else {
            throw new VotingOverException();
        }
    }

    @Override
    public List<RestaurantWithVotes> getRestaurantsWithVotes(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return votesDAO.findAllByVoteDateEquals(date).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream()
                .map(a -> new RestaurantWithVotes(a.getKey().getName(), a.getValue()))
                .sorted(Comparator.comparingLong(RestaurantWithVotes::getVotes))
                .collect(Collectors.toList());
    }
}
