package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.Restaurant;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by MSI on 13.09.2017.
 */
public interface RestaurantDAO extends JpaRepository<Restaurant,String> {
    List<Restaurant> findAllByOrderByNameAsc();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu")
    Set<Restaurant> findAllEager();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu WHERE r.name=?1")
    Restaurant findOneEager(String name);
}
