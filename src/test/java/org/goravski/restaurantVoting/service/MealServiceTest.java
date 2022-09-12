package org.goravski.restaurantVoting.service;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.RestaurantTestData;
import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.model.Meal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goravski.restaurantVoting.MealTestData.*;
import static org.goravski.restaurantVoting.UserTestData.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(scripts = {"classpath:db/initDB_hsql.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    void create() {
        Meal actual = service.create(getNew(), RestaurantTestData.RESTAURANT1_ID);
        Meal expected = getNew();
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "restaurant")
                .isEqualTo(expected);
    }

    @Test
    void update() {
        Meal actual = getUpdatedMeal();
        service.update(actual, RestaurantTestData.RESTAURANT1_ID);
        assertThat(service.get(Meal1_ID))
                .usingRecursiveComparison()
                .ignoringFields("restaurant")
                .isEqualTo(actual);
    }

    @Test
    void delete() {
        service.delete(Meal1_ID);
        assertThrows(NotFoundException.class, () -> service.get(Meal1_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Meal actual = service.get(Meal1_ID);
        assertEquals(actual, meal1);
        System.out.println("actual " + actual);
        System.out.println("expected "+meal1);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("restaurant")
                .isEqualTo(meal1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Meal> expected = List.of(meal1, meal2);
        List<Meal> actual = service.getAll();
        assertEquals(actual, expected);
    }
}