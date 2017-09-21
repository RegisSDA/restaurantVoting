package com.github.regissda.restaurantvoting.converters;

import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.model.Vote;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import com.github.regissda.restaurantvoting.to.VoteTO;

/**
 * Created by MSI on 14.09.2017.
 */
public class Converter {

    public static <T,R> T convert(R input,Class<T> target){
        try {
            T result = target.newInstance();
            if (result instanceof VoteTO) {
                Vote source = (Vote) input;
                ((VoteTO) result).setId(source.getId());
                ((VoteTO) result).setRestaurant(source.getRestaurant().getName());
                ((VoteTO) result).setDate(source.getVoteDate().toString());
            }
            if (result instanceof RestaurantLight) {
                Restaurant source = (Restaurant) input;
                ((RestaurantLight) result).setName(source.getName());
            }
            if (result instanceof RestaurantWithDishes) {
                Restaurant source = (Restaurant) input;
                ((RestaurantWithDishes) result).setName(source.getName());
                ((RestaurantWithDishes) result).setDishes(source.getMenu());
            }


            return result;
        }catch(InstantiationException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }
}
