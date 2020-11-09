package com.jones.mars.repository;

import com.jones.mars.model.DepartmentUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentUserMapper extends BaseMapper<DepartmentUser> {
    void deleteByDepartmentParam(Object param);
}
