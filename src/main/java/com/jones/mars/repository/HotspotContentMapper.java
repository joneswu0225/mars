package com.jones.mars.repository;

import com.jones.mars.model.HotspotContent;
import com.jones.mars.model.param.HotspotContentParam;
import com.jones.mars.model.param.HotspotContentParams;
import com.jones.mars.model.param.HotspotContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotContentMapper extends CommonMapper<HotspotContent> {
    void insertByHotspotContentParams(HotspotContentParams params);
    List<HotspotContent> findByHotspotId(String hotspotId);
    void deleteByHotspotId(String hotspotId);
    void updateHotspotContentSeq(HotspotContentSeqParam param);
    void insertOne(HotspotContentParam param);
    void updateOne(HotspotContentParam param);
    Integer findMaxSeqByhotspotId(String hotspotId);

}
