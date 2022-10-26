package org.goravski.restaurantVoting.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@Entity
@NamedEntityGraph(name = Restaurant.GRATH_WITH_MEALS, attributeNodes = {@NamedAttributeNode("meals")})
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    public static final String GRATH_WITH_MEALS = "RestaurantWithMeals";

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonManagedReference
    private List<Meal> meals;

    public Restaurant(Integer id, String name) {
        super(id, name);
        meals = Collections.emptyList();
    }

        public Restaurant(Integer id, String name, List<Meal> meals) {
            super(id, name);
            this.meals = meals;
        }

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.meals);
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name=" + name +
                ", meals=" + meals +
                "} ";
    }
}
