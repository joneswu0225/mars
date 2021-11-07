package com.jones.mars.repository;

import com.jones.mars.model.BlockHotspot;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockHotspotMapper {
    void insert(Object param);
    Integer findMaxSeqByBlockId(Integer blockId);
    List<BlockHotspot> findAll(Query query);
    void updateBlockHotspotSeq(Object param);
    void delete(Integer id);
    void update(Object param);
}
