package com.jones.mars.repository;

import com.jones.mars.model.BlockContent;
import com.jones.mars.model.param.BlockContentParam;
import com.jones.mars.model.param.BlockContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockContentMapper extends BaseMapper<BlockContent> {
    List<BlockContent> findByBlockId(Integer hotspotId);
    void deleteByBlockId(Integer hotspotId);
    void updateBlockContentSeq(BlockContentSeqParam param);
    Integer findMaxSeqByBlockId(Integer hotspotId);

}
