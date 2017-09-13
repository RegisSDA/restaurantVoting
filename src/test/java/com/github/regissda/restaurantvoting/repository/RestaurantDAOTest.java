package com.github.regissda.restaurantvoting.repository;

import com.github.regissda.restaurantvoting.model.Dish;
import com.github.regissda.restaurantvoting.model.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by MSI on 13.09.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-db.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantDAOTest {

    @Autowired
    private RestaurantDAO restaurantDAO;
    @Test
    public void getDishTest(){
        Restaurant rest = restaurantDAO.findOne("testrest1");
        for(Dish d:rest.getMenu()){
            System.out.println("Name: "+d.getName()+" Price: "+d.getPrice());
        }
    }

    @Test
    public void saveWithDishesTest(){
        Restaurant rest = new Restaurant();
        rest.setName("saveTestRest");
        List<Dish> dishes = new LinkedList<>();
        for (int i=1;i<4;i++){
            dishes.add(new Dish("saveTestDish"+i,500+100*i));
        }
        rest.setMenu(dishes);
        restaurantDAO.save(rest);

        Restaurant getedRest = restaurantDAO.findOne(rest.getName());
        for(Dish d:getedRest.getMenu()){
            System.out.println(d.getName()+" : "+d.getPrice());
        }
    }

}