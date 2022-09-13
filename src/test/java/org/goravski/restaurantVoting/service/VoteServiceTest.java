package org.goravski.restaurantVoting.service;

import org.goravski.restaurantVoting.RestaurantTestData;
import org.goravski.restaurantVoting.exception.NotAcceptableDateException;
import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.model.AbstractBaseEntity;
import org.goravski.restaurantVoting.model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goravski.restaurantVoting.UserTestData.NOT_FOUND;
import static org.goravski.restaurantVoting.UserTestData.USER_ID;
import static org.goravski.restaurantVoting.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(scripts = {"classpath:db/initDB_hsql.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {
    @Autowired
    private VoteService service;

    @Test
    void create() {
        Vote created = service.create(newVote(), USER_ID, RestaurantTestData.RESTAURANT1_ID);
        int id = created.id();
        Vote newVote = newVote();
        newVote.setId(id);
        assertEquals(created, newVote);
    }

    @Test
    void update() {
        Vote actual = getUpdatedVote(ACCEPTABLE_TIME);
        service.update(actual, USER_ID, RestaurantTestData.RESTAURANT1_ID);
        assertThat(service.get(VOTE1_ID, USER_ID))
                .usingRecursiveComparison()
                .ignoringFields("restaurant", "user")
                .isEqualTo(actual);
    }

    @Test
    void updateIncorrectTime() {
        assertThrows(NotAcceptableDateException.class,
                () -> service.update(getUpdatedVote(NOT_ACCEPTABLE_TIME), USER_ID, RestaurantTestData.RESTAURANT1_ID));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class,
                () -> service.update(getUpdatedVote(ACCEPTABLE_TIME), USER_ID + 1, RestaurantTestData.RESTAURANT1_ID));
    }

    @Test
    void delete() {
        service.delete(VOTE1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID, USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    void get() {
        Vote actual = service.get(VOTE1_ID, USER_ID);
        assertEquals(actual, vote1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    void getAll() {
        List<Vote> expected = votes;
        List<Vote> actual = service.getAll(USER_ID);
        actual.sort(Comparator.comparing(AbstractBaseEntity::getId));
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("user", "restaurant")
                .isEqualTo(expected);

    }
}