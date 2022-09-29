package org.goravski.restaurantVoting.json;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.MealTestData;
import org.goravski.restaurantVoting.RestaurantTestData;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.model.Restaurant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.goravski.restaurantVoting.MealTestData.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(meal1);
        log.info(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        assertEquals(meal, meal1);
    }
    @Test
    void readWriteValues (){
        List<Meal> meals = List.of(MealTestData.meal1, meal2);
        String json = JsonUtil.writeValue(meals);
        List<Meal> meals2 = JsonUtil.readValues(json, Meal.class);
        assertEquals(meals, meals2);
    }

    @Test
    void readWriteValueWithNull() {
        String json = JsonUtil.writeValue(RestaurantTestData.restaurant1);
        log.info(json);
        Restaurant restaurant = JsonUtil.readValue(json, Restaurant.class);
        log.info(restaurant.toString());
        assertEquals(restaurant, RestaurantTestData.restaurant1);
    }
}