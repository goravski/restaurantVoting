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
    private final JpaVoteRepository voteRepository;
    private final JpaRestaurantRepository restaurantRepository;
    private final JpaUserRepository userRepository;

    public VoteRepositoryImpl(JpaVoteRepository voteRepository, JpaRestaurantRepository restaurantRepository,
                              JpaUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int id) {
        if (!vote.isNew() && get(vote.id()) == null) {
            return null;
        }
        vote.setRestaurant(restaurantRepository.getReferenceById(id));
        return voteRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return voteRepository.delete(id) != 0;
    }

    @Override
    public Vote get(int id) {
        return voteRepository.findById(id).orElse(null);
    }

    @Override
    public Vote getByString(String string) {
        throw new NotSupportedException("Method save (T t, int authId) not supported for VoteRepository");
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    @Override
    public int getVotesByRestaurant(int restaurantId) {
        return voteRepository.getVotesByRestaurant(restaurantId);
    }
}
