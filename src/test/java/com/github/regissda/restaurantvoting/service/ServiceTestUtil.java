package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;

import static com.github.regissda.restaurantvoting.repository.TestUtil.*;

/**
 * Created by MSI on 24.09.2017.
 */
public class ServiceTestUtil {

    public static final RestaurantLight RESTAURANT_LIGHT_1 = new RestaurantLight("testrest1");
    public static final RestaurantLight RESTAURANT_LIGHT_2 = new RestaurantLight("testrest2");
    public static final RestaurantLight RESTAURANT_LIGHT_3 = new RestaurantLight("testrest3");


    public static final RestaurantWithDishes RESTAURANT_WITH_DISHES_1 = new RestaurantWithDishes("testrest1", DISH_1_1, DISH_1_2, DISH_1_3);
    public static final RestaurantWithDishes RESTAURANT_WITH_DISHES_2 = new RestaurantWithDishes("testrest2", DISH_2_1, DISH_2_2, DISH_2_3);
    public static final RestaurantWithDishes RESTAURANT_WITH_DISHES_3 = new RestaurantWithDishes("testrest3", DISH_3_1, DISH_3_2, DISH_3_3);

    public static RestaurantWithDishes createResturantWithDishes() {
        return new RestaurantWithDishes("createdRestaurant", DISH_1_1, DISH_2_2, DISH_3_3);
    }

    public static RestaurantWithDishes updateRestaurantWithDishes(RestaurantWithDishes restaurantWithDishes) {
        return new RestaurantWithDishes(restaurantWithDishes.getName(), DISH_1_2, DISH_2_3, DISH_3_1);
    }
}
