package org.goravski.restaurantVoting;

import org.goravski.restaurantVoting.model.Restaurant;

import static org.goravski.restaurantVoting.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int NOT_FOUND = 10;
    public static final int RESTAURANT1_ID = START_SEQ + 3;


    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "restaurant1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "restaurant2", MealTestData.meal2);


    public static Restaurant getNew() {
        return new Restaurant(null, "new");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setName("updateName");
        return updated;
    }
}
