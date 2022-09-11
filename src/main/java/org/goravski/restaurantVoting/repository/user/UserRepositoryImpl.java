package org.goravski.restaurantVoting.repository.user;

import org.goravski.restaurantVoting.exception.NotSupportedException;
import org.goravski.restaurantVoting.model.User;
import org.goravski.restaurantVoting.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements AbstractRepository<User> {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public User save(User user, int id) {
        throw new NotSupportedException("This method is not supported in UserRepositoryImpl.class");
    }

    @Override
    public boolean delete(int id) {
        return jpaUserRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return jpaUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByString(String email) {
        return jpaUserRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return jpaUserRepository.findAll();
    }
}
