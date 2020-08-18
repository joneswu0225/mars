package com.jones.mars.repository;

import com.jones.mars.model.Hotspot;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectHotspotMapper {
    void insert(Object param);
    void insertMany(Object param);
    void delete(Object param);
    void updateProjectHotspotSeq(Object param);
    Integer findMaxSeqByHotspot(Hotspot hotspot);
}
