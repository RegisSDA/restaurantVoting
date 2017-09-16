package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.exceptions.NotFoundException;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;
import static com.github.regissda.restaurantvoting.web.AdminRestController.REST_ADMIN_URL;

/**
 * Created by MSI on 14.09.2017.
 */
@Controller
@RequestMapping(value = REST_ADMIN_URL + "/restaurants")
public class AdminRestController {
    static final String REST_ADMIN_URL = "/rest/v1/admin";

    @Autowired
    private RestaurantDAO restaurantDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantLight>> getRestaurants() {
        return new ResponseEntity<>(restaurantDAO.findAllByOrderByNameAsc().stream().map(a -> convert(a, RestaurantLight.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithDishes> getRestaurant(@PathVariable("name") String name) {
        Restaurant restaurant = restaurantDAO.findOneEager(name);
        if (restaurant == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(convert(restaurant, RestaurantWithDishes.class),HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity createOrUpdateRestaurant(@RequestBody RestaurantWithDishes restaurantTO) {
        Restaurant restaurant = new Restaurant(restaurantTO.getName(), restaurantTO.getDishes());
        restaurantDAO.save(restaurant);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity deleteRestaurant(@PathVariable("name") String name) {
        restaurantDAO.delete(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
