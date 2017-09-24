package com.github.regissda.restaurantvoting.service;

import com.github.regissda.restaurantvoting.matcher.BeanMatcher;
import com.github.regissda.restaurantvoting.model.Restaurant;
import com.github.regissda.restaurantvoting.to.RestaurantLight;
import com.github.regissda.restaurantvoting.to.RestaurantWithDishes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import static com.github.regissda.restaurantvoting.service.ServiceTestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by MSI on 24.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql",config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantsServiceImplTest {

    @Autowired
    private RestaurantsService restaurantsService;
    private BeanMatcher matcherLight = BeanMatcher.of(RestaurantLight.class);
    private BeanMatcher matcherWithDishes = BeanMatcher.of(RestaurantWithDishes.class);

    @Before
    public void setUp() throws Exception {
        restaurantsService.evictCache();
    }

    @Test
    public void getRestaurants() throws Exception {
        matcherLight.assertEquals(Arrays.asList(RESTAURANT_LIGHT_1,RESTAURANT_LIGHT_2,RESTAURANT_LIGHT_3),restaurantsService.getRestaurants());
    }

    @Test
    public void getRestaurantWithDishes() throws Exception {
        matcherWithDishes.assertEquals(RESTAURANT_WITH_DISHES_1,restaurantsService.getRestaurantWithDishes("testrest1"));
    }

    @Test
    public void create() throws Exception {
        RestaurantWithDishes created = createResturantWithDishes();
        restaurantsService.create(created);
        matcherWithDishes.assertEquals(Arrays.asList(created,RESTAURANT_WITH_DISHES_1,RESTAURANT_WITH_DISHES_2,RESTAURANT_WITH_DISHES_3),
                                        restaurantsService.getRestaurantsWithDishes());
    }

    @Test
    public void update() throws Exception {
        RestaurantWithDishes updated = updateRestaurantWithDishes(RESTAURANT_WITH_DISHES_1);
        restaurantsService.update(updated);
        matcherWithDishes.assertEquals(Arrays.asList(updated,RESTAURANT_WITH_DISHES_2,RESTAURANT_WITH_DISHES_3),
                restaurantsService.getRestaurantsWithDishes());
    }

    @Test
    public void delete() throws Exception {
        restaurantsService.delete(RESTAURANT_LIGHT_1.getName());
        matcherLight.assertEquals(Arrays.asList(RESTAURANT_LIGHT_2,RESTAURANT_LIGHT_3),restaurantsService.getRestaurants());
    }

    @Test
    public void getRestaurantsWithDishes() throws Exception {
        matcherWithDishes.assertEquals(Arrays.asList(RESTAURANT_WITH_DISHES_1,RESTAURANT_WITH_DISHES_2,RESTAURANT_WITH_DISHES_3),
                restaurantsService.getRestaurantsWithDishes());
    }

    @Test
    public void getTop() throws Exception {
        matcherWithDishes.assertEquals(RESTAURANT_WITH_DISHES_3,restaurantsService.getTop(LocalDate.of(2017,9,11)));
    }

}