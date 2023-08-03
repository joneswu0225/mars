package com.jones.mars.service;

import com.jones.mars.model.param.BlockAttachmentContentParam;
import com.jones.mars.model.param.BlockAttachmentContentSeqParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockAttachmentContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlockAttachmentContentService extends BaseService{

    @Autowired
    private BlockAttachmentContentMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }


    public BaseResponse insertContent(BlockAttachmentContentParam param){
        Integer maxSeq = mapper.findMaxSeqByAttachmentId(param.getAttachmentId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        mapper.insert(param);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", param.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse updateContent(BlockAttachmentContentParam param){
        mapper.update(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateBlockAttachmentContentSeq(BlockAttachmentContentSeqParam param){
        mapper.updateAttachmentContentSeq(param);
        return BaseResponse.builder().build();
    }

}


