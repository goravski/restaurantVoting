package org.goravski.restaurantVoting.repository;

import java.util.List;

public interface AbstractAuthorizedRepository <T>{
    T save(T t, int authId);

    boolean delete(int idT, int authId);

    T get(int idT, int authId);

    T getByString(String string, int authId);

    List<T> getAll(int authId);
}
