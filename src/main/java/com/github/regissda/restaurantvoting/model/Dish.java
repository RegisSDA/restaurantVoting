package com.github.regissda.restaurantvoting.model;

import javax.persistence.Embeddable;

/**
 * Created by MSI on 13.09.2017.
 */
@Embeddable
public class Dish {
    private String name;
    private int price;

    public Dish() {
    }

    public Dish(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
