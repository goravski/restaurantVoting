package org.goravski.restaurantVoting.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;



@NoArgsConstructor
@Entity
@NamedEntityGraph(name = Restaurant.GRATH_WITH_MEALS, attributeNodes = {@NamedAttributeNode("meals")})
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    public static final String GRATH_WITH_MEALS = "RestaurantWithMeals";
    @Getter
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private List<Meal> meals;

    @Getter
    @OneToMany( mappedBy = "restaurant")
    @OrderBy("dateVote DESC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List <Vote> votes;


    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name);
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals.isEmpty() ? List.of() : List.copyOf(meals);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name=" + name +
//                ", meals=" + meals +
                "} ";
    }
}
