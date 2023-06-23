package com.jones.mars.repository;

import com.jones.mars.model.BlockContent;
import com.jones.mars.model.BlockExamineContent;
import com.jones.mars.model.param.BlockContentSeqParam;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockExamineContentMapper extends BaseMapper<BlockExamineContent> {
    List<BlockExamineContent> findByExamineId(Integer examineId);
    void deleteByExamineId(Integer examineId);
    void updateExamineContentSeq(BlockExamineContentSeqParam param);
    Integer findMaxSeqByExamineId(Integer examineId);

}
