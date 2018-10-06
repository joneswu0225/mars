package com.jones.mars.repository;

import com.jones.mars.model.DepartmentUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentUserMapper {
    void insert(Object param);
    void delete(Object param);
    List<DepartmentUser> findDepartmentUser(Integer departmentId);
}
