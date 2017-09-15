package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.exceptions.NotFoundException;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static com.github.regissda.restaurantvoting.converters.Converter.convert;
import static com.github.regissda.restaurantvoting.web.AdminRestController.REST_ADMIN_URL;

/**
 * Created by MSI on 14.09.2017.
 */
@RestController
@RequestMapping(value = REST_ADMIN_URL + "/restaurants")
public class AdminRestController {
    static final String REST_ADMIN_URL = "/rest/v1/admin";

    @Autowired
    private RestaurantDAO restaurantDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantLight> getRestaurants() {
        return restaurantDAO.findAllByOrderByNameAsc().stream().map(a -> convert(a, RestaurantLight.class)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantWithDishes getRestaurant(@PathVariable("name") String name) {
        Restaurant restaurant = restaurantDAO.findOne(name);
        if (restaurant == null) {
            throw new NotFoundException();
        }
        return convert(restaurantDAO.findOne(name), RestaurantWithDishes.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrUpdateRestaurant(@RequestBody RestaurantWithDishes restaurantTO) {
        Restaurant restaurant = new Restaurant(restaurantTO.getName(), restaurantTO.getDishes());
        restaurantDAO.save(restaurant);
    }


    @DeleteMapping(value = "/{name}")
    public void deleteRestaurant(@PathVariable("name") String name) {
        restaurantDAO.delete(name);
    }

}
