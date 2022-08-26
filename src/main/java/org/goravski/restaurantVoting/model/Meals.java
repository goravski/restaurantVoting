package org.goravski.restaurantVoting.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Meals extends AbstractNamedEntity {
    private int price;
    private LocalDateTime date;
    private Restaurant restaurant;

}
