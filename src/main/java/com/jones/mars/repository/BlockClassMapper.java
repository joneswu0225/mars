package com.jones.mars.repository;

import com.jones.mars.model.BlockClass;
import com.jones.mars.model.param.BlockClassParam;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockClassMapper extends BaseMapper<BlockClass> {
    void updateBlockClassSeq(BlockClassParam param);
}
