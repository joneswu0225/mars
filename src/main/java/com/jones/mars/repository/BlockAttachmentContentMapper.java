package com.jones.mars.repository;

import com.jones.mars.model.BlockAttachmentContent;
import com.jones.mars.model.param.BlockAttachmentContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockAttachmentContentMapper extends CommonMapper<BlockAttachmentContent> {

    List<BlockAttachmentContent> findByAttachmentId(Long examineId);
    void deleteByAttachmentId(Long examineId);
    void updateAttachmentContentSeq(BlockAttachmentContentSeqParam param);
    Integer findMaxSeqByAttachmentId(Long examineId);

}
