package org.goravski.restaurantVoting.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Getter
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonManagedReference
    private List<Meal> meals;

    @Getter
    @OneToMany(mappedBy = "restaurant")
    @OrderBy("dateVote DESC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Vote> votes;


    public Restaurant(Integer id, String name) {
        super(id, name);
        meals = Collections.emptyList();
        votes = Collections.emptyList();
    }

        public Restaurant(Integer id, String name, List<Meal> meals, List<Vote> votes) {
            super(id, name);
            this.meals = meals;
            this.votes = votes;
        }

    public Restaurant(Restaurant r) {
        this(r.id, r.name);
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
