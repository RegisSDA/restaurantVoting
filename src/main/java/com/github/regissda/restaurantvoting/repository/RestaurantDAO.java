package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by MSI on 13.09.2017.
 */
public interface RestaurantDAO extends JpaRepository<Restaurant,String> {
    List<Restaurant> findAllByOrderByNameAsc();
}
