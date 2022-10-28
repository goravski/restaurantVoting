package org.goravski.restaurantVoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static org.goravski.restaurantVoting.model.Vote.GRATH_RESTAURANT_JOIN;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "votes")
@NamedEntityGraph(name = GRATH_RESTAURANT_JOIN, attributeNodes = {@NamedAttributeNode("restaurant")})

public class Vote extends AbstractBaseEntity {
    public static final String GRATH_RESTAURANT_JOIN = "RestaurantJoin";
    public static final String GRATH_USER_JOIN = "UserJoin";

    @Column(name = "date_vote", nullable = false)
    @NotNull
    private LocalDateTime dateVote;

    @Column(name = "vote")
    @Getter
    private boolean isVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    public Vote(Integer id, LocalDateTime dateVote, boolean isVote) {
        super(id);
        this.dateVote = dateVote;
        this.isVote = isVote;
    }
    public Vote (Integer id, LocalDateTime dateVote, boolean isVote, Restaurant restaurant, User user){
        this (id, dateVote, isVote);
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(Vote vote) {
        this(vote.id, vote.dateVote, vote.isVote, vote.restaurant, vote.user);
    }

    public Vote(Integer id, LocalDateTime dateVote) {
        super(id);
        this.dateVote = dateVote;
    }

}
