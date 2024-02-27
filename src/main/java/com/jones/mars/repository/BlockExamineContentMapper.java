package com.jones.mars.repository;

import com.jones.mars.model.BlockContent;
import com.jones.mars.model.BlockExamineContent;
import com.jones.mars.model.param.BlockContentSeqParam;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockExamineContentMapper extends CommonMapper<BlockExamineContent> {
    List<BlockExamineContent> findByExamineId(String examineId);
    void deleteByExamineId(String examineId);
    void updateExamineContentSeq(BlockExamineContentSeqParam param);
    Integer findMaxSeqByExamineId(String examineId);

}
