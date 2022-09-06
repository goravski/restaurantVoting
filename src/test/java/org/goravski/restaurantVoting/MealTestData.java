package org.goravski.restaurantVoting;

import org.goravski.restaurantVoting.model.Meal;

import java.time.Month;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.of;

public class MealTestData {

    public static final int Meal1_ID = RestaurantTestData.RESTAURANT1_ID + 2;

    public static final Meal meal1 = new Meal(
            Meal1_ID, "Potatoes", 10.0, of(2020, Month.JANUARY, 30, 10, 0)
    );
    public static final Meal meal2 = new Meal(
            Meal1_ID + 1, "Soup", 15.0, of(2020, Month.JANUARY, 30, 10, 0)
    );

    public static Meal getNew() {
        return new Meal(null, "new", 1.0, of(2020, Month.JANUARY, 30, 10, 0));
    }

    public static Meal getNewPriceNull() {
        return new Meal(null, "new", 0.0, of(2020, Month.JANUARY, 30, 10, 0));
    }

    public static Meal getUpdated() {
        return new Meal(Meal1_ID, "updatedMeal", 20.0, meal1.getDate().plus(1, ChronoUnit.MINUTES));
    }

}
