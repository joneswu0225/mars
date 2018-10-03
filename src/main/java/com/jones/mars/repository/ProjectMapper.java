package com.jones.mars.repository;

import com.jones.mars.model.Project;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper extends BaseMapper<Project> {
    List<Object> findAllName(Query query);
}
