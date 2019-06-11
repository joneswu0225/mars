package com.jones.mars.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectHotspotMapper {
    void insert(Object param);
    void delete(Object param);
    void updateProjectHotspotSeq(Object param);
}
