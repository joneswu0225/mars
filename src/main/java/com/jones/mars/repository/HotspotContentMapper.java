package com.jones.mars.repository;

import com.jones.mars.model.HotspotContent;
import com.jones.mars.model.param.HotspotContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotContentMapper extends BaseMapper<HotspotContent> {
    List<HotspotContent> findByHotspotId(Integer hotspotId);

    void updateHotspotContentSeq(HotspotContentSeqParam param);
}
