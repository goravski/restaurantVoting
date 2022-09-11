package org.goravski.restaurantVoting.repository.meal;

import org.goravski.restaurantVoting.exception.NotSupportedException;
import org.goravski.restaurantVoting.model.Meal;
import org.goravski.restaurantVoting.repository.restaurant.JpaRestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealRepositoryImpl implements MealRepository {
    private final JpaMealRepository mealRepository;
    private JpaRestaurantRepository restaurantRepository;

    public MealRepositoryImpl(JpaMealRepository mealRepository, JpaRestaurantRepository restaurantRepository) {
        this.mealRepository = mealRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }


    @Override
    public Meal save(Meal meal, int restaurantId) {
        if (!meal.isNew() && restaurantRepository.findById(restaurantId) == null) {
            return null;
        }
        meal.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return mealRepository.save(meal);
    }

    @Override
    public boolean delete(int id) {
        return mealRepository.delete(id) != 0;
    }

    @Override
    public Meal get(int id) {
        return mealRepository.findById(id).orElse(null);
    }

    @Override
    public Meal getByString(String string) {
        throw new NotSupportedException("This method doesn't supported");
    }

    @Override
    public List<Meal> getAll() {
        return mealRepository.findAll();
    }
}
