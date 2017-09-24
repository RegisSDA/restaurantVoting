package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.to.RestaurantWithVotes;
import com.github.regissda.restaurantvoting.to.VoteTO;
import org.springframework.cache.annotation.CacheEvict;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by MSI on 23.09.2017.
 */

public interface VotesService {

    List<VoteTO> getVotesOfUser(String user);

    VoteTO getVote(String name, LocalDate date);

    Vote create(VoteTO voteTO, String name);

    void delete(Integer id, String name);

    List<RestaurantWithVotes> getRestaurantsWithVotes(LocalDate date);

    void update(VoteTO voteTO, String userName);
}
