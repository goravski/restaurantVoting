package org.goravski.restaurantVoting.repository;

import org.goravski.restaurantVoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @EntityGraph(value = Restaurant.GRATH_WITH_MEALS)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.meals WHERE r.id=?1")
    Restaurant getWithMeals(int id);
}
