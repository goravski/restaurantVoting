package org.goravski.restaurantVoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Meal(Integer id, String name, double price, LocalDateTime date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public Meal(Meal m) {
        this(m.id, m.name, m.price, m.date);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", restaurant =" + restaurant +
                "} ";
    }
}
