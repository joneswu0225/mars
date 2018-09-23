package com.jones.mars.repository;

import com.jones.mars.model.Department;
import com.jones.mars.model.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper extends BaseMapper<Department> {
    List<Object> findAllName(Query query);
}
