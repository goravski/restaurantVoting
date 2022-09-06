package org.goravski.restaurantVoting.repository;

import org.goravski.restaurantVoting.exception.NotSupportedException;
import org.goravski.restaurantVoting.model.Meal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealRepositoryImpl implements AbstractRepository<Meal> {
    private final JpaMealRepository repository;

    public MealRepositoryImpl(JpaMealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Meal get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Meal getByString(String string) {
        throw new NotSupportedException("This method doesn't supported");
    }

    @Override
    public List <Meal> getAll() {
        return repository.findAll();
    }
}
