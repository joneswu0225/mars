package com.jones.mars.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPartnerMapper {
    void insert(Object param);
    void delete(Object param);
    void update(Object param);
}
