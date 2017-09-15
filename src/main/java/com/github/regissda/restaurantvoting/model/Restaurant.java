package com.github.regissda.restaurantvoting.model;

import javax.persistence.*;
import java.util.List;



/**
 * Created by MSI on 13.09.2017.
 */

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    private String name;
    @ElementCollection(targetClass = Dish.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "rest_menu")
    @JoinColumn(name = "restaurant_name")
    private List<Dish> menu;

    public Restaurant() {
    }

    public Restaurant(String name, List<Dish> menu) {

        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }
}
