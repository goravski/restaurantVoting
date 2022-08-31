package org.goravski.restaurantVoting.service;

import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.model.AbstractBaseEntity;
import org.goravski.restaurantVoting.model.Role;
import org.goravski.restaurantVoting.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;


import static org.goravski.restaurantVoting.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@Sql(scripts = {"classpath:db/initDB_hsql.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void get() {
        User user = service.get(ADMIN_ID);
        assertEquals(user, admin);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        assertEquals(user, admin);
    }

    @Test
    void create() {
        User created = service.create(getNew());
        User newUser = getNew();
        userMatcher(created, newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        assertThat(service.get(USER_ID))
                .usingRecursiveComparison()
                .ignoringFields("registered")
                .isEqualTo(updated);
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        List<User> expected = Arrays.asList(admin, user, guest);
        expected.sort(Comparator.comparing(AbstractBaseEntity::getId));
        assertTrue(all.containsAll(expected));
        assertThat(all)
                .usingRecursiveComparison()
                .ignoringFields("registered", "roles")
                .isEqualTo(expected);
    }
}