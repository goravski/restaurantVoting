package org.goravski.restaurantVoting.service;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.model.Restaurant;
import org.goravski.restaurantVoting.repository.MealRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.goravski.restaurantVoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@Slf4j
public class MealService {
   private final MealRepositoryImpl repository;

    public MealService(MealRepositoryImpl repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        log.debug("create(Meal={}) method start", meal.toString());
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal);

    }

    public void update(Meal meal) {
        log.debug("update(Meal={}) method start", meal);
        Assert.notNull(meal, "meal must not be null");
        repository.save(meal);
        log.debug("update(Meal={}) method end", meal);
    }

    public void delete(int id) {
        log.debug("delete(Meal id={}) method start", id);
        checkNotFoundWithId(repository.delete(id), id);
        log.debug("delete(Meal id={}) method end", id);
    }

    public Meal get(int id) {
        log.debug("get(Meal id={}) method start", id);
        return checkNotFoundWithId(repository.get(id), id);

    }

    public List<Meal> getAll() {
        log.debug("getAll <Meal> method start");
        return repository.getAll();
    }
}
