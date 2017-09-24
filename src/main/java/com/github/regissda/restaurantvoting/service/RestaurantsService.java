package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.VoteTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by MSI on 23.09.2017.
 */

public interface RestaurantsService {
    List<RestaurantLight> getRestaurants();

    RestaurantWithDishes getRestaurantWithDishes(String name);

    Restaurant create(RestaurantWithDishes restaurantWithDishes);

    void delete(String name);

    List<RestaurantWithDishes> getRestaurantsWithDishes();

    RestaurantWithDishes getTop(LocalDate date);

    void update(RestaurantWithDishes restaurantWithDishes);

    void evictCache();
}
