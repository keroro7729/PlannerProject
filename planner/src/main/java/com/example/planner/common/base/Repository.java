package com.example.planner.common.base;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    T save(T object);

    Optional<T> findById(Long id);

    List<T> findAll();

    Optional<T> update(Long id, T object);

    Boolean delete(Long id);
}
