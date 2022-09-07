package org.goravski.restaurantVoting.repository;

import org.goravski.restaurantVoting.exception.NotSupportedException;
import org.goravski.restaurantVoting.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements AbstractRepository<Restaurant> {
    private final JpaRestaurantRepository repository;

    public RestaurantRepositoryImpl(JpaRestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @Override
    public Restaurant save(Restaurant restaurant, int id) {
        throw new NotSupportedException("This method is not supported in RestaurantRepositoryImpl.class");
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Restaurant getByString(String email) throws NotSupportedException{
        throw new NotSupportedException("This method doesn't supported");
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    public Restaurant getWithMeals(int id){
        return repository.getWithMeals(id);
    }
}
