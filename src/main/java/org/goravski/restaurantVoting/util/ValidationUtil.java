package org.goravski.restaurantVoting.util;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.exception.NotAcceptableDateException;
import org.goravski.restaurantVoting.exception.NotFoundException;
import org.goravski.restaurantVoting.model.AbstractBaseEntity;
import org.goravski.restaurantVoting.model.Vote;

@Slf4j
public class ValidationUtil {
    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        log.debug ("checkNotFoundWithId({}, id={}) start", found, id);
        checkNotFound(found, "id=" + id);
        log.debug ("checkNotFoundWithId({}, id={}) end", found, id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkTime (Vote vote){
        log.debug("checkTime ({}) START", vote.getDateVote());
        checkOutOfTime (vote.getDateVote().toLocalTime().compareTo(ConstantUtil.MAX_TIME_FOR_VOTING) >= 0);
        log.debug("checkTime ({}) END", vote.getDateVote());
    }

    private static void checkOutOfTime(boolean timeIs) {
        if(timeIs){
            throw new NotAcceptableDateException(
                    String.format("Voting time is ended at %s o'clock", ConstantUtil.MAX_TIME_FOR_VOTING)
            );
        }
    }
    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {

        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}

