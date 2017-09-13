package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.github.regissda.restaurantvoting.web.AdminRestController.REST_ADMIN_URL;

/**
 * Created by MSI on 14.09.2017.
 */
@RestController
@RequestMapping(value = REST_ADMIN_URL+"/restaurants")
public class AdminRestController {
    static final String REST_ADMIN_URL = "/rest/v1/admin";

    @Autowired
    private RestaurantDAO restaurantDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getRestaurants(){
        return restaurantDAO.findAllByOrderByNameAsc();
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable("name") String name){
        return restaurantDAO.findOne(name);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrUpdateRestaurant(@RequestBody Restaurant restaurant){
        restaurantDAO.save(restaurant);
    }
    @DeleteMapping(value = "/{name}")
    public void deleteRestaurant(@PathVariable("name") String name){
        restaurantDAO.delete(name);
    }

}
