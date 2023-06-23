package com.jones.mars.service;

import com.jones.mars.model.param.BlockExamineContentParam;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockExamineContentMapper;
import com.jones.mars.repository.BlockExamineContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlockExamineContentService extends BaseService{

    @Autowired
    private BlockExamineContentMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }


    public BaseResponse insertContent(BlockExamineContentParam param){
        Integer maxSeq = mapper.findMaxSeqByExamineId(param.getExamineId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        mapper.insert(param);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", param.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse updateContent(BlockExamineContentParam param){
        mapper.update(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateBlockExamineContentSeq(BlockExamineContentSeqParam param){
        mapper.updateExamineContentSeq(param);
        return BaseResponse.builder().build();
    }

}


