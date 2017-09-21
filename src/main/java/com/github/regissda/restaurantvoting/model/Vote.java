package com.github.regissda.restaurantvoting.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by MSI on 13.09.2017.
 */
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_login")
    private User user;
    @ManyToOne
    @JoinColumn(name = "restaurant_name")
    private Restaurant restaurant;
    private LocalDate voteDate;


    public Vote() {
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate voteDate) {

        this.id=id;
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", voteDate=" + voteDate +
                '}';
    }
}
