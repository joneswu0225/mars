package com.jones.mars.repository;

import com.jones.mars.model.Scene;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SceneMapper extends BaseMapper<Scene> {
    List<Scene> findAllName(Query query);
}

