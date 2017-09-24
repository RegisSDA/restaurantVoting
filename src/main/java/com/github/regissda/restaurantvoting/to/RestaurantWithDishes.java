package com.github.regissda.restaurantvoting.to;

import com.github.regissda.restaurantvoting.model.Dish;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MSI on 14.09.2017.
 */
public class RestaurantWithDishes {
    private String name;
    private List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantWithDishes(String name, Dish... dishes) {
        this.name = name;
        this.dishes = Arrays.asList(dishes);//тк ТО, не нужна модифицируемость
    }

    public RestaurantWithDishes() {
    }

    @Override
    public String toString() {
        return "RestaurantWithDishes{" +
                "name='" + name + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
