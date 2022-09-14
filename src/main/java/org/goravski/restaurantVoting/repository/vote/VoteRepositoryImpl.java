package org.goravski.restaurantVoting.repository.vote;

import org.goravski.restaurantVoting.exception.NotSupportedException;
import org.goravski.restaurantVoting.model.Vote;
import org.goravski.restaurantVoting.repository.restaurant.JpaRestaurantRepository;
import org.goravski.restaurantVoting.repository.user.JpaUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {
    private final JpaVoteRepository  voteRepository;
    private final JpaRestaurantRepository restaurantRepository;
    private final JpaUserRepository userRepository;

    public VoteRepositoryImpl(JpaVoteRepository voteRepository, JpaRestaurantRepository restaurantRepository,
                              JpaUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Vote save(Vote vote, int authId) {
        throw new NotSupportedException("Method save (T t, int authId) not supported for VoteRepository");
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int authId, int id) {
        if (!vote.isNew() && get(vote.id(), authId) == null) {
            return null;
        }
        vote.setRestaurant(restaurantRepository.getReferenceById(id));
        vote.setUser(userRepository.getReferenceById(authId));
        return voteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int authId) {
        return voteRepository.delete(id, authId) != 0;
    }

    @Override
    public Vote get(int id, int authId) {
        return voteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == authId)
                .orElse(null);
    }

    @Override
    public Vote getByString(String string, int authId) {
        throw new NotSupportedException("Method save (T t, int authId) not supported for VoteRepository");
    }

    @Override
    public List<Vote> getAll(int authId) {
        return voteRepository.getAll(authId);
    }
}
