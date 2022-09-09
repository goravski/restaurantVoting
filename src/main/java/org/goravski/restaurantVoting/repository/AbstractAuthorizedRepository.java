package org.goravski.restaurantVoting.repository;

import java.util.List;

public interface AbstractAuthorizedRepository <T>{
    T save(T t, int authId);

    T save(T t, int authId, int id);

    boolean delete(int id, int authId);

    T get(int idT, int authId);

    T getByString(String string, int authId);

    List<T> getAll(int authId);
}
