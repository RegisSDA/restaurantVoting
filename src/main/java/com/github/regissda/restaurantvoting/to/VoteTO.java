package com.github.regissda.restaurantvoting.to;

/**
 * Created by MSI on 14.09.2017.
 */
public class VoteTO {
    private Integer id;
    private String restaurant;
    private String date;

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VoteTO{" +
                "id=" + id +
                ", restaurant='" + restaurant + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public VoteTO() {
    }

    public VoteTO(Integer id, String restaurant, String date) {

        this.id = id;
        this.restaurant = restaurant;
        this.date = date;
    }
}
