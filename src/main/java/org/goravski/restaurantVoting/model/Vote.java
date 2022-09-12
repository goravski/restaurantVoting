package org.goravski.restaurantVoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity{

    @Column(name = "date_vote", nullable = false)
    @NotNull
    private LocalDateTime dateVote;

    @Column(name = "vote")
    private boolean isVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote(Integer id, LocalDateTime dateVote, boolean isVote,  Restaurant restaurant, User user) {
        super(id);
        this.dateVote = dateVote;
        this.isVote = isVote;
        this.restaurant = restaurant;
        this.user = user;
    }
    public Vote (Vote vote){
        this(vote.id, vote.dateVote, vote.isVote, vote.restaurant, vote.user);
    }
}
