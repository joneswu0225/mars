package com.jones.mars.mapper;

import com.jones.mars.model.UserVisit;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVisitMapper {
    void insert(UserVisit paramUserVisit);
}
