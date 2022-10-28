package org.goravski.restaurantVoting.repository;

import java.util.List;

public interface AbstractRepository <T>{
    T save(T t);

    T save(T t, int id);

    boolean delete(int id);

    T get(int id);

    T getByString(String string);

    List<T> getAll();

    default int getVotesByRestaurant (int restaurantId){
        throw new UnsupportedOperationException();
    };

}