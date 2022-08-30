package org.goravski.restaurantVoting.repository;

import org.goravski.restaurantVoting.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{
    JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
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
    public User getByEmail(String email) {
        return jpaUserRepository.getUserByEmail(email).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return jpaUserRepository.findAll();
    }
}
