package com.jones.mars.repository;

import com.jones.mars.model.BlockContent;
import com.jones.mars.model.BlockExamineContent;
import com.jones.mars.model.param.BlockContentSeqParam;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockExamineContentMapper extends CommonMapper<BlockExamineContent> {
    List<BlockExamineContent> findByExamineId(Long examineId);
    void deleteByExamineId(Long examineId);
    void updateExamineContentSeq(BlockExamineContentSeqParam param);
    Integer findMaxSeqByExamineId(Long examineId);

}
