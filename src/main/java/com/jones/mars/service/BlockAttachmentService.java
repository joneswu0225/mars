package com.jones.mars.service;

import com.jones.mars.model.BlockAttachment;
import com.jones.mars.model.query.BlockAttachmentQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockAttachmentContentMapper;
import com.jones.mars.repository.BlockAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockAttachmentService extends BaseService{

    @Autowired
    private BlockAttachmentMapper mapper;
    @Autowired
    private BlockAttachmentContentMapper blockAttachmentContentMapper;


    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Integer blockId){
        Query query = BlockAttachmentQuery.builder().blockId(blockId).build();
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse findBlockAttachments(BlockAttachmentQuery query){
        return findByPage(query);
    }

    public BaseResponse findBlockAttachmentById(Integer attachmentId){
        BlockAttachment attachment = mapper.findOne(attachmentId);
        attachment.setBlockAttachmentContentList(blockAttachmentContentMapper.findByAttachmentId(attachment.getId()));
        return BaseResponse.builder().data(attachment).build();
    }

    public BaseResponse deleteAttachment(Integer attachmentId){
        super.delete(attachmentId);
        blockAttachmentContentMapper.deleteByAttachmentId(attachmentId);
        return BaseResponse.builder().build();
    }

}

