package org.goravski.restaurantVoting.service;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.Vote;
import org.goravski.restaurantVoting.repository.vote.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.goravski.restaurantVoting.util.ValidationUtil.checkNotFoundWithId;
import static org.goravski.restaurantVoting.util.ValidationUtil.checkTime;

@Service
@Slf4j
public class VoteService {
    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote) {
        log.debug("create(Vote={}) method start", vote);
        Assert.notNull(vote, "vote must not be null");
        checkTime(vote);
        return repository.save(vote);
    }

    public void update(Vote vote) {
        log.debug("update(Vote={}) method START", vote);
        Assert.notNull(vote, "vote must not be null");
        checkTime(vote);
        repository.save(vote);
        log.debug("update(Vote={}) method END", vote);
    }

    public void delete(int id) {
        log.debug("delete(Vote id={}) method START", id);
        checkNotFoundWithId(repository.delete(id), id);
        log.debug("delete(Vote id={}) method END", id);
    }

    public List<Vote> getAll() {
        return repository.getAll();
    }

    public Vote get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }
    public int countVotes (int restaurantId){
        return  repository.getVotesByRestaurant(restaurantId);
    }
}
