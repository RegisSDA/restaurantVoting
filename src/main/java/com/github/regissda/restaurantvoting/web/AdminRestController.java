package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.AuthorizedUser;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.service.RestaurantsService;
import com.github.regissda.restaurantvoting.service.UserService;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

import static com.github.regissda.restaurantvoting.web.AdminRestController.REST_ADMIN_URL;

/**
 * Created by MSI on 14.09.2017.
 */
@Controller
@RequestMapping(value = REST_ADMIN_URL)
public class AdminRestController {
    static final String REST_ADMIN_URL = "/rest/v1/admin";

    @Autowired
    private RestaurantsService restaurantsService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantLight>> getRestaurants() {
        return new ResponseEntity<>(restaurantsService.getRestaurants(), HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantWithDishes> getRestaurant(@PathVariable("name") String name) {
        return new ResponseEntity<>(restaurantsService.getRestaurantWithDishes(name), HttpStatus.OK);
    }


    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createRestaurant(@RequestBody RestaurantWithDishes restaurantTO) {

        Restaurant saved = restaurantsService.create(restaurantTO);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_ADMIN_URL + "/restaurants/{id}")
                .buildAndExpand(saved.getName()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(null);

    }

    @PutMapping(value = "/restaurants/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRestaurant(@RequestBody RestaurantWithDishes restaurantWithDishes, @PathVariable("name") String name) {
        if (!name.equals(restaurantWithDishes.getName())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        restaurantsService.update(restaurantWithDishes);
        return new ResponseEntity(HttpStatus.OK);
    }


    @DeleteMapping(value = "/restaurants/{name}")
    public ResponseEntity deleteRestaurant(@PathVariable("name") String name) {
        restaurantsService.delete(name);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTO> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping(value = "/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTO> getUser(@PathVariable("login") String login) {
        return new ResponseEntity<>(userService.get(login), HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@RequestBody UserTO userTO) {
        userService.save(userTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{login}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody UserTO userTO, @PathVariable("login") String login) {
        if (!userTO.getLogin().equals(login)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (userTO.getLogin().equals(AuthorizedUser.getUserName())) {
            userService.selfUpdate(userTO);
        } else {
            userService.update(userTO);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{login}")
    public ResponseEntity deleteUser(@PathVariable("login") String login) {
        userService.delete(login);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
