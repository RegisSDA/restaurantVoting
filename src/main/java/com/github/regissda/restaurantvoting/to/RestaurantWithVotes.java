package com.github.regissda.restaurantvoting.to;

/**
 * Created by MSI on 14.09.2017.
 */
public class RestaurantWithVotes {
    private String name;
    private long votes;

    public RestaurantWithVotes() {
    }

    public RestaurantWithVotes(String name, long votes) {

        this.name = name;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "RestaurantWithVotes{" +
                "name='" + name + '\'' +
                ", votes=" + votes +
                '}';
    }
}
