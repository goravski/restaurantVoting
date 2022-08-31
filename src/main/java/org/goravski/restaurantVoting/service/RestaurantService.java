package org.goravski.restaurantVoting.service;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Restaurant;
import org.goravski.restaurantVoting.repository.RestaurantRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.goravski.restaurantVoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepositoryImpl repository;

    public RestaurantService(RestaurantRepositoryImpl repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        log.debug("create(Restaurant={}) method start", restaurant.toString());
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);

    }

    public void update(Restaurant restaurant) {
        log.debug("update(Restaurant={}) method start", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        repository.save(restaurant);
        log.debug("update(Restaurant={}) method end", restaurant);
    }

    public void delete(int id) {
        log.debug("delete(id={}) method start", id);
        checkNotFoundWithId(repository.delete(id), id);
        log.debug("delete(id={}) method end", id);
    }

    public Restaurant get(int id) {
        log.debug("get(id={}) method start", id);
        return checkNotFoundWithId(repository.get(id), id);

    }

    public List<Restaurant> getAll() {
        log.debug("getAll method start");
        return repository.getAll();
    }

    public Restaurant getWithMeals(int id) {
        log.debug("getWithMeals(id={}) method start", id);
        return checkNotFoundWithId(repository.getWithMeals(id), id);
    }
}
