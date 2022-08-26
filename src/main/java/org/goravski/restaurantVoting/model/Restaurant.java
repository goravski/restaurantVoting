package org.goravski.restaurantVoting.model;


import lombok.*;


@ToString @NoArgsConstructor @AllArgsConstructor
public class Restaurant extends AbstractNamedEntity {
    private int votes;
}
