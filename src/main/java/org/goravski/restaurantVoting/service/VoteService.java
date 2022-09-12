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

    public Vote create(Vote vote, int userId, int restaurantId) {
        log.debug("create(Vote={}) method start", vote);
        Assert.notNull(vote, "vote must not be null");
        checkTime(vote);
        return repository.save(vote, userId, restaurantId);
    }

    public void update(Vote vote, int userId, int restaurantId) {
        log.debug("update(Vote={}, userId={}, restaurantId={}) method START", vote, userId, restaurantId);
        Assert.notNull(vote, "vote must not be null");
        checkTime(vote);
        checkNotFoundWithId(repository.save(vote, userId, restaurantId), userId);
        log.debug("update(Vote={}, userId={}, restaurantId={}) method END", vote, userId, restaurantId);
    }

    public void delete(int id, int userId) {
        log.debug("delete(Vote id={}, userId={}) method START", id, userId);
        checkNotFoundWithId(repository.delete(id, userId), id);
        log.debug("delete(Vote id={}, userId={}) method END", id, userId);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }
}
