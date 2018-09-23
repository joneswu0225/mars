package com.jones.mars.repository;

import com.jones.mars.model.HotspotContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotContentMapper extends BaseMapper<HotspotContent> {
    List<HotspotContent> findByHotspotId(Integer hotspotId);
}
