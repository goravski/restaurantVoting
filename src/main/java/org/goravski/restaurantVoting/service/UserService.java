package org.goravski.restaurantVoting.service;

import lombok.extern.slf4j.Slf4j;
import org.goravski.restaurantVoting.model.User;
import org.goravski.restaurantVoting.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static org.goravski.restaurantVoting.util.ValidationUtil.*;

@Slf4j
@Service
public class UserService {

    private final UserRepositoryImpl repository;

    public UserService(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

    public void delete(int id) {
        log.debug("delete(id={}) method start", id);
        checkNotFoundWithId(repository.delete(id), id);
        log.debug("delete(id={}) method end", id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        return repository.getByString(email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
