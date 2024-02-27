package com.jones.mars.service;

import com.jones.mars.model.param.BlockTourSpotParam;
import com.jones.mars.model.param.BlockTourSpotSeqParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BlockTourSpotMapper;
import com.jones.mars.repository.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BlockTourSpotService extends BaseService{

    @Autowired
    private BlockTourSpotMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }


    public BaseResponse insertContent(BlockTourSpotParam param){
        Integer maxSeq = mapper.findMaxSeqByBlockId(param.getBlockId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        mapper.insert(param);
        Map<String, String> map = new HashMap<>();
        map.put("id", param.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse updateContent(BlockTourSpotParam param){
        mapper.update(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateBlockTourSpotSeq(BlockTourSpotSeqParam param){
        mapper.updateBlockTourSpotSeq(param);
        return BaseResponse.builder().build();
    }

}


