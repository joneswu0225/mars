package com.jones.mars.repository;

import com.jones.mars.model.BlockAttachmentContent;
import com.jones.mars.model.param.BlockAttachmentContentSeqParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockAttachmentContentMapper extends BaseMapper<BlockAttachmentContent> {
    List<BlockAttachmentContent> findByAttachmentId(Integer examineId);
    void deleteByAttachmentId(Integer examineId);
    void updateAttachmentContentSeq(BlockAttachmentContentSeqParam param);
    Integer findMaxSeqByAttachmentId(Integer examineId);

}
