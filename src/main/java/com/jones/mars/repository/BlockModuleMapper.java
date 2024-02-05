package com.jones.mars.repository;

import com.jones.mars.model.BlockModule;
import com.jones.mars.model.param.BlockModuleParam;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockModuleMapper extends CommonMapper<BlockModule> {
    void updateBlockModuleSeq(BlockModuleParam param);
}
