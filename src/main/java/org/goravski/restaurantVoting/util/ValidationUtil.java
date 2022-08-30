package org.goravski.restaurantVoting.util;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.exception.NotFoundException;
@Slf4j
public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        log.debug ("checkNotFoundWithId(bool) start");
        checkNotFound(found, "id=" + id);
        log.debug ("checkNotFoundWithId(bool) end");
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }
}

