package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.User;
import com.github.regissda.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by MSI on 13.09.2017.
 */
public interface VotesDAO extends JpaRepository<Vote,Integer>{
    List<Vote> findAllByUserEquals(User user);

    Vote getByVoteDateAndUser(LocalDate date, User one);

    List<Vote> findAllByVoteDateEquals(LocalDate now);
}

