package org.goravski.restaurantVoting.repository;

import java.util.List;

public interface AbstractRepository <T>{
    T save(T t);

    boolean delete(int id);

    T get(int id);

    T getByString(String string);

    List<T> getAll();

}