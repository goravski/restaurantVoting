package org.goravski.restaurantVoting.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/admin/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMealController {
    @Autowired
    private MealService mealService;

    @GetMapping
    public List<Meal> getAll() {
        log.info("getAll()");
        return mealService.getAll();
    }

    @GetMapping("/{id}")
    public Meal getById(@PathVariable int id) {
        log.info("Method GET: getById({})", id);
        return mealService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete({})", id);
        mealService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Meal> create(@RequestBody Meal eMeal) {
//        log.info("create(meal={})", eMeal);
        Meal newMeal = new Meal(eMeal);
        newMeal.setRestaurant(eMeal.getRestaurant());
        Meal createdMeal = mealService.create(newMeal);
        URI uriCreatedMeal = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/meals/{id}")
                .buildAndExpand(createdMeal.getId())
                .toUri();
        return ResponseEntity.created(uriCreatedMeal).body(createdMeal);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int id, @RequestBody Meal eMeal) {
//        log.info("update(meal={}, id={})", eMeal, id);
        mealService.update(eMeal);
    }
}
