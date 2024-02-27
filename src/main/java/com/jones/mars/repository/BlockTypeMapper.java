package com.jones.mars.repository;

import com.jones.mars.model.BlockType;
import com.jones.mars.model.param.BlockTypeSeqParam;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTypeMapper extends CommonMapper<BlockType> {
    List<BlockType> findAllName(Query query);
    void updateBlockTypeSeq(BlockTypeSeqParam param);
    Integer findMaxSeqByEnterpriseId(String enterprise);
}
