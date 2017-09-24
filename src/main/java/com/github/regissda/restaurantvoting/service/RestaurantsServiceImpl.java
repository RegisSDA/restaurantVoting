package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.exceptions.AlreadyExist;
import com.github.regissda.restaurantvoting.exceptions.NotFoundException;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import com.github.regissda.restaurantvoting.repository.VotesDAO;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.VoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;

/**
 * Created by MSI on 23.09.2017.
 */
@Service
public class RestaurantsServiceImpl implements RestaurantsService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Autowired
    private VotesDAO votesDAO;

    @Override
    public List<RestaurantLight> getRestaurants() {
        return restaurantDAO.findAllByOrderByNameAsc().stream()
                .map(a -> convert(a, RestaurantLight.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantWithDishes getRestaurantWithDishes(String name) {
        Restaurant restaurant = restaurantDAO.findOneEager(name);
        if (restaurant == null) {
            throw new NotFoundException();
        }
        return convert(restaurant, RestaurantWithDishes.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(RestaurantWithDishes restaurantWithDishes) {
        Restaurant restaurant = restaurantDAO.findOne(restaurantWithDishes.getName());
        if (restaurant != null) {
            throw new AlreadyExist();
        }
        return restaurantDAO.save(convert(restaurantWithDishes, Restaurant.class));
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(RestaurantWithDishes restaurantWithDishes) {
        restaurantDAO.save(convert(restaurantWithDishes, Restaurant.class));
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(String name) {
        restaurantDAO.delete(name);
    }

    @Override
    @Cacheable("restaurants")
    public List<RestaurantWithDishes> getRestaurantsWithDishes() {
        return restaurantDAO.findAllEager().stream().map(a -> convert(a, RestaurantWithDishes.class)).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "top1")
    public RestaurantWithDishes getTop(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        Optional<Map.Entry<Restaurant, Long>> top = votesDAO.findAllByVoteDateEquals(date).stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator
                        .comparingLong((Map.Entry<Restaurant, Long> a) -> a.getValue())
                        .thenComparing((Map.Entry<Restaurant, Long> a) -> a.getKey().getName()));

        RestaurantWithDishes restaurant = null;
        if (top.isPresent()) {
            restaurant = convert(restaurantDAO.findOneEager(top.get().getKey().getName()), RestaurantWithDishes.class);
        }
        return restaurant;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "top1"), @CacheEvict(value = "restaurants")})
    public void evictCache() {
    }
}
