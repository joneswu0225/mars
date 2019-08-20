package com.jones.mars.repository;

import com.jones.mars.model.UserRole;
import com.jones.mars.model.param.UserRoleParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    void insert(Object param);
    List<UserRole> findAll(Integer roleId);
    void delete(Object param);
}
