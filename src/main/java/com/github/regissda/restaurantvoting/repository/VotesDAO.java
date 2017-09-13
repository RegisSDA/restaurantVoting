package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by MSI on 13.09.2017.
 */
public interface VotesDAO extends JpaRepository<Vote,Integer>{
}
