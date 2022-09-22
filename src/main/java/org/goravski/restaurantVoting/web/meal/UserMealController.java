package org.goravski.restaurantVoting.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserMealController {
    @Autowired
    private MealService mealService;

    @GetMapping("/{id}")
    public Meal getById(@PathVariable int id) {
        log.info("getById({})", id);
        return mealService.get(id);
    }
}
