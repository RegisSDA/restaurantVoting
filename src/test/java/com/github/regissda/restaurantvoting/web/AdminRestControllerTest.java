package com.github.regissda.restaurantvoting.web;

import com.github.regissda.restaurantvoting.model.Dish;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.repository.RestaurantDAO;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by MSI on 15.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml",
                                    "classpath:spring-test.xml"})
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class AdminRestControllerTest {

    @Autowired
    private AdminRestController adminRestController;

    @Test
    public void createOrUpdateRestaurant() throws Exception {
        RestaurantWithDishes restaurant = new RestaurantWithDishes();
        restaurant.setName("unitTestRest1");
        List<Dish> menu = new LinkedList<>();
        for (int i =0; i<4;i++){
            menu.add(new Dish("dish"+i,500+150*i));
        }
        restaurant.setDishes(menu);
        adminRestController.createOrUpdateRestaurant(restaurant);

        RestaurantWithDishes restaurantWithDishes = adminRestController.getRestaurant(restaurant.getName());
        System.out.println(restaurantWithDishes.getName());
        System.out.println();
        for (Dish d:restaurantWithDishes.getDishes()){
            System.out.println(d.getName());
        }
    }

}