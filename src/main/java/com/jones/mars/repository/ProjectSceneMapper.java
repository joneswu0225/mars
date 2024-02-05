package com.jones.mars.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSceneMapper {
    void insert(Object param);
    void insertOne(Object param);
    void delete(Object param);
    void updateProjectSceneSeq(Object param);
    Integer findMaxSeqByProjectId(Long projectId);
}
