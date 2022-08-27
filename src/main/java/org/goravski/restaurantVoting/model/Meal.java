package org.goravski.restaurantVoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
