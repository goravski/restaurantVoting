package org.goravski.restaurantVoting;

import org.goravski.restaurantVoting.model.Vote;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.LocalDateTime.*;

import static org.goravski.restaurantVoting.MealTestData.*;
import static org.goravski.restaurantVoting.RestaurantTestData.*;
import static org.goravski.restaurantVoting.UserTestData.*;

public class VoteTestData {
    public static final int VOTE1_ID = Meal1_ID + 2;
    public static final LocalDateTime ACCEPTABLE_TIME = of(2020, Month.JANUARY, 30, 10, 0);
    public static final LocalDateTime NOT_ACCEPTABLE_TIME = of(2020, Month.JANUARY, 30, 11, 1);

    public static Vote vote1 = new Vote(VOTE1_ID, ACCEPTABLE_TIME, true, restaurant1, user);
    public static Vote vote2 = new Vote(VOTE1_ID + 1, ACCEPTABLE_TIME.plusDays(1), true, restaurant1, user);

    public static Vote getUpdatedVote(LocalDateTime dateTime) {
        Vote updated = new Vote(vote1);
        updated.setDateVote(dateTime.plus(2, ChronoUnit.MINUTES));
        return updated;
    }

    public static Vote newVote() {
        return new Vote(null, of(2020, Month.FEBRUARY, 1, 10, 0));
    }

    public static List<Vote> votes = List.of(vote1, vote2);
}
