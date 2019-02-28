package com.jones.mars.repository;

import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.query.ProjectUserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserMapper {
    void insert(Object param);
    void delete(Object param);
    void update(Object param);
    List<ProjectUser> findList(ProjectUserQuery query);
}
