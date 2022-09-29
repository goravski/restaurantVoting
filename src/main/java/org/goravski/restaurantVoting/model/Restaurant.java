package org.goravski.restaurantVoting.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;


@NoArgsConstructor
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

    public Restaurant(Integer id, String name, Meal... meal) {
        super(id, name);
        this.meals = List.of(meal);
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name);
    }

    public void setMeals(Meal meal) {
        meals.add(meal);
    }

    public void setVotes(Vote vote) {
        votes.add(vote);
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
