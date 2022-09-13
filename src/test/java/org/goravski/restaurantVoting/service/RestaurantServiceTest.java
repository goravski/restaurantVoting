package org.goravski.restaurantVoting.service;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.MealTestData;
import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.model.Restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

import static org.goravski.restaurantVoting.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(scripts = {"classpath:db/initDB_hsql.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {
    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        Restaurant expected = getNew();
        assertThat(created).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
        log.debug("created = {}, expected={}", created.toString(), expected.toString());
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        assertThat(service.get(RESTAURANT1_ID))
                .usingRecursiveComparison()
                .ignoringFields("meals", "votes")
                .isEqualTo(updated);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant actual = service.get(RESTAURANT1_ID);
        assertEquals(actual, restaurant1);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("meals", "votes")
                .isEqualTo(restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        List <Restaurant> expected = List.of(restaurant1, restaurant2);
        List<Restaurant> all = service.getAll();
        log.debug("expected={}", expected);
        log.debug("actual={}", all);
        assertThat(all).usingRecursiveComparison()
                .ignoringFields("meals", "votes")
                .isEqualTo(expected);
    }

    @Test
    void getWithMeals() {
        Restaurant actual = service.getWithMeals(restaurant1.getId());
        Restaurant expected = restaurant1;
        log.debug("expected={}", expected);
        log.debug("actual={}", actual);
        assertEquals(actual, expected);

    }
}