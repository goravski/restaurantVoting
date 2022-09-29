package org.goravski.restaurantVoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Restaurant;
import org.goravski.restaurantVoting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.goravski.restaurantVoting.util.ValidationUtil.assureIdConsistent;

@Slf4j
@RestController
@RequestMapping(value = "/admin/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    @Autowired
    private RestaurantService service;

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping("/{id}/with-meals")
    public Restaurant getWithMeals(@PathVariable int id) {
        log.info("getWithMeals {}", id);
        return service.getWithMeals(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody Restaurant restaurant) {
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant eRestaurant) {
        Restaurant restaurant = new Restaurant(eRestaurant);
        Restaurant restaurantCreated = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/restaurant/id")
                .buildAndExpand(restaurantCreated.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurantCreated);
    }

}
