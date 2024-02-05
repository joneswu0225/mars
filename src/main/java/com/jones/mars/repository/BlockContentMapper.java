package com.jones.mars.repository;

import com.jones.mars.model.BlockContent;
import com.jones.mars.model.param.BlockContentParam;
import com.jones.mars.model.param.BlockContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockContentMapper extends CommonMapper<BlockContent> {
    List<BlockContent> findByBlockId(Long blockId);
    void deleteByBlockId(Long blockId);
    void updateBlockContentSeq(BlockContentSeqParam param);
    Integer findMaxSeqByBlockId(Long blockId);

}
