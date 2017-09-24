package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.model.Role;
import com.github.regissda.restaurantvoting.to.*;

import java.time.LocalDate;
import java.util.EnumSet;

import static com.github.regissda.restaurantvoting.model.Role.ROLE_USER;
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

    public static final VoteTO VOTE_TO_1 = new VoteTO(1, RESTAURANT_1.getName(), "2017-09-10");
    public static final VoteTO VOTE_TO_2 = new VoteTO(2, RESTAURANT_1.getName(), "2017-09-10");
    public static final VoteTO VOTE_TO_3 = new VoteTO(3, RESTAURANT_2.getName(), "2017-09-10");
    public static final VoteTO VOTE_TO_4 = new VoteTO(4, RESTAURANT_3.getName(), "2017-09-11");
    public static final VoteTO VOTE_TO_5 = new VoteTO(5, RESTAURANT_3.getName(), "2017-09-11");
    public static final VoteTO VOTE_TO_6 = new VoteTO(6, RESTAURANT_2.getName(), "2017-09-11");

    public static VoteTO createVoteTO() {
        return new VoteTO(null, VOTE_TO_1.getRestaurant(), LocalDate.now().toString());
    }

    public static VoteTO updateVoteTO(VoteTO voteTO) {
        return new VoteTO(voteTO.getId(), RESTAURANT_2.getName(), voteTO.getDate());
    }

    public static final RestaurantWithVotes RESTAURANT_WITH_VOTES_1 = new RestaurantWithVotes(RESTAURANT_LIGHT_1.getName(), 0);
    public static final RestaurantWithVotes RESTAURANT_WITH_VOTES_2 = new RestaurantWithVotes(RESTAURANT_LIGHT_2.getName(), 1);
    public static final RestaurantWithVotes RESTAURANT_WITH_VOTES_3 = new RestaurantWithVotes(RESTAURANT_LIGHT_3.getName(), 2);

    public static final UserTO USER_TO_1 = new UserTO(USER_1.getLogin(), USER_1.getPassword(), USER_1.getRoles());
    public static final UserTO USER_TO_2 = new UserTO(USER_2.getLogin(), USER_2.getPassword(), USER_2.getRoles());
    public static final UserTO USER_TO_3 = new UserTO(USER_3.getLogin(), USER_3.getPassword(), USER_3.getRoles());

    public static UserTO createUserTO() {
        return new UserTO("_createdUser", "createdPass", EnumSet.allOf(Role.class));
    }

    public static UserTO updatedUserTO(UserTO userTO) {
        return new UserTO(userTO.getLogin(), "newPassword", userTO.getRoles());
    }
}
