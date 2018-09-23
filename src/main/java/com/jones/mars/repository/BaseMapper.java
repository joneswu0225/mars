package com.jones.mars.repository;


import com.jones.mars.model.Query;

import java.util.List;

public interface BaseMapper<T> {
    List<T> findList(Query paramQuery);

    Integer findCount(Query paramQuery);

    List<T> findAll();

    void insert(T param);

    void update(T param);

    void delete(Integer id);
}

