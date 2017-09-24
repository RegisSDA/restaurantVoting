package com.github.regissda.restaurantvoting.converters;

import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.model.User;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.UserTO;
import com.github.regissda.restaurantvoting.to.VoteTO;

/**
 * Created by MSI on 14.09.2017.
 */
public class Converter {

    public static <T, R> T convert(R input, Class<T> target) {
        try {
            T result = target.newInstance();
            if (result instanceof VoteTO) {
                Vote source = (Vote) input;
                ((VoteTO) result).setId(source.getId());
                ((VoteTO) result).setRestaurant(source.getRestaurant().getName());
                ((VoteTO) result).setDate(source.getVoteDate().toString());
            } else if (result instanceof RestaurantLight) {
                Restaurant source = (Restaurant) input;
                ((RestaurantLight) result).setName(source.getName());
            } else if (result instanceof RestaurantWithDishes) {
                Restaurant source = (Restaurant) input;
                ((RestaurantWithDishes) result).setName(source.getName());
                ((RestaurantWithDishes) result).setDishes(source.getMenu());
            } else if (result instanceof Restaurant) {
                RestaurantWithDishes source = (RestaurantWithDishes) input;
                ((Restaurant) result).setName(source.getName());
                ((Restaurant) result).setMenu(source.getDishes());
            } else if (result instanceof UserTO) {
                User source = (User) input;
                ((UserTO) result).setLogin(source.getLogin());
                ((UserTO) result).setPassword(source.getPassword());
                ((UserTO) result).setRoles(source.getRoles());
            } else if (result instanceof User) {
                UserTO source = (UserTO) input;
                ((User) result).setLogin(source.getLogin());
                ((User) result).setPassword(source.getPassword());
                ((User) result).setRoles(source.getRoles());
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
