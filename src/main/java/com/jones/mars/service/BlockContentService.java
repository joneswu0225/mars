package com.jones.mars.service;

import com.jones.mars.model.param.BlockContentParam;
import com.jones.mars.model.param.BlockContentSeqParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.BlockContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlockContentService extends BaseService{

    @Autowired
    private BlockContentMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }


    public BaseResponse insertContent(BlockContentParam param){
        Integer maxSeq = mapper.findMaxSeqByBlockId(param.getBlockId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        mapper.insert(param);
        Map<String, String> map = new HashMap<>();
        map.put("id", param.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse updateContent(BlockContentParam param){
        mapper.update(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateBlockContentSeq(BlockContentSeqParam param){
        mapper.updateBlockContentSeq(param);
        return BaseResponse.builder().build();
    }

}


