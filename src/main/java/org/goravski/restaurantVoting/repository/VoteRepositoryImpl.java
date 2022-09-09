package org.goravski.restaurantVoting.repository;

import org.goravski.restaurantVoting.exception.NotSupportedException;
import org.goravski.restaurantVoting.model.Vote;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepositoryImpl implements AbstractAuthorizedRepository<Vote> {
    private JpaVoteRepository voteRepository;
    private JpaRestaurantRepository restaurantRepository;
    private JpaUserRepository userRepository;

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
    public Vote save(Vote vote, int authId, int id) {
        return null;
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
