package com.jones.mars.repository;

import com.jones.mars.model.Scene;
import com.jones.mars.model.param.SceneSeqParam;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SceneMapper extends BaseMapper<Scene> {
    List<Scene> findAllName(Query query);
    void updateSceneSeq(SceneSeqParam param);

    Integer findMaxSeqByBlockId(Integer blockId);

}

