package com.jones.mars.mapper;

import com.jones.mars.model.Query;
import com.jones.mars.model.Scene;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SceneMapper {
    List<Scene> findList(Query paramQuery);

    List<Scene> findAll();

    Integer findCount(Query paramQuery);

    List<Scene> findByTitle(String paramString);

    void update(Scene paramScene);
}

