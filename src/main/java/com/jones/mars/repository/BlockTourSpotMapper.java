package com.jones.mars.repository;

import com.jones.mars.model.BlockTourSpot;
import com.jones.mars.model.param.BlockTourSpotSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTourSpotMapper extends CommonMapper<BlockTourSpot> {
    List<BlockTourSpot> findByBlockId(Long blockId);
    void deleteByBlockId(Long blockId);
    void updateBlockTourSpotSeq(BlockTourSpotSeqParam param);
    Integer findMaxSeqByBlockId(Long blockId);

}
