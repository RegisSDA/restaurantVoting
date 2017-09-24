package com.github.regissda.restaurantvoting.to;

/**
 * Created by MSI on 14.09.2017.
 */
public class RestaurantLight {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantLight() {
    }

    public RestaurantLight(String name) {

        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantLight{" +
                "name='" + name + '\'' +
                '}';
    }
}
