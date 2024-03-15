package com.jones.mars.repository;

import com.jones.mars.model.BlockTip;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockTipMapper extends CommonMapper<BlockTip> {
    Integer findMaxSeqByBlockId(String blockId);
}
