package com.jones.mars.repository;

import com.jones.mars.model.BlockSceneType;
import com.jones.mars.model.query.BlockSceneTypeQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.query.SceneQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockSceneTypeMapper extends BaseMapper<BlockSceneType> {
    List<BlockSceneType> findAllName(Query query);
    List<BlockSceneType> findSceneTypeProjectScene(Query query);
}
