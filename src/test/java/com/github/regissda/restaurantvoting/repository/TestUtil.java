package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.*;

import java.time.LocalDate;
import java.util.Arrays;


/**
 * Created by MSI on 18.09.2017.
 */
public class TestUtil {
    //данные наналогичные данным БД при использовании populateDB.sql
    public static final User USER_1 = new User("testuser1","testuser1", Role.ADMIN, Role.USER);
    public static final User USER_2 = new User("testuser2","testuser2", Role.ADMIN);
    public static final User USER_3 = new User("testuser3","testuser3", Role.USER);

    public static final Dish DISH_1_1 = new Dish("dish1",1000);
    public static final Dish DISH_1_2 = new Dish("dish2",900);
    public static final Dish DISH_1_3 = new Dish("dish3",800);

    public static final Dish DISH_2_1 = new Dish("dish2",1000);
    public static final Dish DISH_2_2 = new Dish("dish3",1100);
    public static final Dish DISH_2_3 = new Dish("dish4",1200);

    public static final Dish DISH_3_1 = new Dish("dish3",500);
    public static final Dish DISH_3_2 = new Dish("dish4",600);
    public static final Dish DISH_3_3 = new Dish("dish5",400);

    public static final Restaurant RESTAURANT_1 = new Restaurant("testrest1",DISH_1_1,DISH_1_2,DISH_1_3);
    public static final Restaurant RESTAURANT_2 = new Restaurant("testrest2",DISH_2_1,DISH_2_2,DISH_2_3);
    public static final Restaurant RESTAURANT_3 = new Restaurant("testrest3",DISH_3_1,DISH_3_2,DISH_3_3);

    public static final Vote VOTE_1 = new Vote(1,USER_1,RESTAURANT_1, LocalDate.of(2017,9,10));
    public static final Vote VOTE_2 = new Vote(2,USER_2,RESTAURANT_1, LocalDate.of(2017,9,10));
    public static final Vote VOTE_3 = new Vote(3,USER_3,RESTAURANT_2, LocalDate.of(2017,9,10));
    public static final Vote VOTE_4 = new Vote(4,USER_1,RESTAURANT_3, LocalDate.of(2017,9,11));
    public static final Vote VOTE_5 = new Vote(5,USER_2,RESTAURANT_3, LocalDate.of(2017,9,11));
    public static final Vote VOTE_6 = new Vote(6,USER_3,RESTAURANT_2, LocalDate.of(2017,9,11));

    public static Vote getCreatedVote(){
        return new Vote(null,USER_1,RESTAURANT_1,LocalDate.of(2017,9,15));
    }

    public static Vote getUpdatedVote(Vote vote){
        Vote updatedVote = new Vote(vote.getId(),vote.getUser(),vote.getRestaurant(),vote.getVoteDate());
        return updatedVote;
    }

    public static Restaurant getCreatedRestaurant(){
        return new Restaurant("newRestaurant", Arrays.asList(DISH_1_1,DISH_2_2,DISH_3_3));
    }
    public static Restaurant getUpdateRestaurant(Restaurant restaurant){
        return new Restaurant(restaurant.getName(), Arrays.asList(DISH_1_2,DISH_2_3,DISH_3_1));
    }

    public static User getCreatedUser(){
        return new User("createdUser","password",Role.USER);
    }

    public static User getUpdatedUser(User user){
        return new User(user.getLogin(),"updatedPassword",Role.USER,Role.ADMIN);
    }

}
