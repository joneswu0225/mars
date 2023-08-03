package com.jones.mars.repository;

import com.jones.mars.model.BlockAttachment;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockAttachmentMapper extends BaseMapper<BlockAttachment> {
    List<Object> findAllName(Query query);
}
