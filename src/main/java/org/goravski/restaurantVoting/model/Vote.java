package org.goravski.restaurantVoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static org.goravski.restaurantVoting.model.Vote.GRATH_RESTAURANT_JOIN;
import static org.goravski.restaurantVoting.model.Vote.GRATH_USER_JOIN;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "votes")
@NamedEntityGraph(name = GRATH_RESTAURANT_JOIN, attributeNodes = {@NamedAttributeNode("restaurant")})
@NamedEntityGraph(name = GRATH_USER_JOIN, attributeNodes = {@NamedAttributeNode("user")})
public class Vote extends AbstractBaseEntity {
    public static final String GRATH_RESTAURANT_JOIN = "RestaurantJoin";
    public static final String GRATH_USER_JOIN = "UserJoin";

    @Column(name = "date_vote", nullable = false)
    @NotNull
    private LocalDateTime dateVote;

    @Column(name = "vote")
    private boolean isVote = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote(Integer id, LocalDateTime dateVote, boolean isVote, Restaurant restaurant, User user) {
        super(id);
        this.dateVote = dateVote;
        this.isVote = isVote;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(Vote vote) {
        this(vote.id, vote.dateVote, vote.isVote, vote.restaurant, vote.user);
    }
}
