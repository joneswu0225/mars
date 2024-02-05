package com.jones.mars.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.ProjectHotspot;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectHotspotMapper extends BaseMapper<ProjectHotspot> {
    void insertMany(Object param);
    void delete(Object param);
    void updateProjectHotspotSeq(Object param);
    Integer findMaxSeqByHotspot(Hotspot hotspot);
}
