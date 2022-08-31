package org.goravski.restaurantVoting;

import org.goravski.restaurantVoting.model.Role;
import org.goravski.restaurantVoting.model.User;

import java.util.Collections;
import java.util.Date;

import static org.goravski.restaurantVoting.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);
    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static boolean userMatcher(User user1, User user2) {
        if (!user1.equals(user2)) {
            return false;
        } else if (!user1.getName().equals(user2.getName())) {
            return false;
        } else if (!user1.getEmail().equals(user2.getEmail())) {
            return false;
        } else if (!user1.getPassword().equals(user2.getPassword())) {
            return false;
        } else if (!user1.getRoles().equals(user2.getRoles())) {
            return false;
        } else {
            return true;
        }
    }

}
