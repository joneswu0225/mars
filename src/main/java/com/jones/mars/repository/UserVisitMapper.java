package com.jones.mars.repository;

import com.jones.mars.model.UserVisit;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitMapper {
    void insert(UserVisit paramUserVisit);
}
