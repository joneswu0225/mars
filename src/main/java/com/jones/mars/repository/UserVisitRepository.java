package com.jones.mars.repository;

import com.jones.mars.mapper.UserVisitMapper;
import com.jones.mars.model.UserVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserVisitRepository {

    @Autowired
    private UserVisitMapper userVisitMapper;

    public void insert(UserVisit userVisit) {
        this.userVisitMapper.insert(userVisit);
    }
}

