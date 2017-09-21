package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.matcher.BeanMatcher;
import com.github.regissda.restaurantvoting.model.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.regissda.restaurantvoting.repository.TestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by MSI on 21.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantDAOTest {

    @Autowired
    private RestaurantDAO restaurantDAO;

    private BeanMatcher matcherEager = BeanMatcher.of(Restaurant.class,(expected, actual) -> (
            expected.getName().equals(actual.getName())
            && expected.getMenu().equals(actual.getMenu())
            ));
    private BeanMatcher matcherLazy = BeanMatcher.of(Restaurant.class,(expected, actual) -> (
            expected.getName().equals(actual.getName())
            ));

    @Test
    public void findAllByOrderByNameAsc() throws Exception {
        List<Restaurant> restaurants = restaurantDAO.findAllByOrderByNameAsc();
        matcherLazy.assertEquals(Arrays.asList(RESTAURANT_1,RESTAURANT_2,RESTAURANT_3),restaurants);
    }

    @Test
    public void findAllEager() throws Exception {
        matcherEager.assertEquals(Arrays.asList(RESTAURANT_1,RESTAURANT_2,RESTAURANT_3),new ArrayList<>(restaurantDAO.findAllEager()));
    }

    @Test
    public void findOneEager() throws Exception {
        Restaurant restaurant = restaurantDAO.findOneEager(RESTAURANT_1.getName());
        matcherEager.assertEquals(RESTAURANT_1,restaurant);
    }

    @Test
    public void testSave(){
        Restaurant restaurant = getCreatedRestaurant();
        restaurantDAO.save(restaurant);
        matcherEager.assertEquals(restaurant,restaurantDAO.findOneEager(restaurant.getName()));
    }

    @Test
    public void testDelete(){
        restaurantDAO.delete(RESTAURANT_1.getName());
        matcherEager.assertEquals(Arrays.asList(RESTAURANT_2,RESTAURANT_3),new ArrayList<>(restaurantDAO.findAllEager()));

    }

    @Test
    public void testUpdate(){
        Restaurant updated = getUpdateRestaurant(RESTAURANT_1);
        restaurantDAO.save(updated);
        matcherEager.assertEquals(Arrays.asList(updated,RESTAURANT_2,RESTAURANT_3),new ArrayList<>(restaurantDAO.findAllEager()));
    }

    @Test
    public void testFindOne(){
        assertEquals(RESTAURANT_1.getName(),restaurantDAO.findOne(RESTAURANT_1.getName()).getName());
    }

    @Test
    public void testNotFound(){
        assertTrue(null==restaurantDAO.findOne("notFountRest"));
    }
}