package org.goravski.restaurantVoting.service;

import org.goravski.restaurantVoting.UserTestData;
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
import static org.goravski.restaurantVoting.RestaurantTestData.*;
import static org.goravski.restaurantVoting.UserTestData.*;
import static org.goravski.restaurantVoting.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(scripts = {"classpath:db/initDB_hsql.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {
    @Autowired
    private VoteService service;

    @Test
    void create() {
        Vote expected = newVote();
        expected.setUser(user);
        expected.setRestaurant(restaurant1);
        Vote created = service.create(expected);
        int id = created.id();
        expected.setId(id);
        assertEquals(created, expected);
    }

    @Test
    void update() {
        Vote actual = getUpdatedVote(ACCEPTABLE_TIME);
        actual.setUser(user);
        actual.setRestaurant(restaurant1);
        service.update(actual);
        assertThat(service.get(VOTE1_ID))
                .usingRecursiveComparison()
                .ignoringFields("restaurant", "user")
                .isEqualTo(actual);
    }

    @Test
    void updateIncorrectTime() {
        assertThrows(NotAcceptableDateException.class,
                () -> service.update(getUpdatedVote(NOT_ACCEPTABLE_TIME)));
    }

    @Test
    void delete() {
        service.delete(VOTE1_ID);
        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(UserTestData.NOT_FOUND));
    }

    @Test
    void get() {
        Vote actual = service.get(VOTE1_ID);
        assertEquals(actual, vote1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Vote> expected = votes;
        List<Vote> actual = service.getAll();
        actual.sort(Comparator.comparing(AbstractBaseEntity::getId));
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("user", "restaurant")
                .isEqualTo(expected);

    }

    @Test
    void countVotesTest() {
        int expected = 2;
        int result = service.countVotes(RESTAURANT1_ID);
        assertEquals(expected, result);
    }
}