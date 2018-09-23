package com.jones.mars.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSceneMapper {
    void insert(Object param);
    void delete(Object param);
}
