package com.jones.mars.service;

import com.jones.mars.model.param.BlockTipParam;
import com.jones.mars.model.param.BlockTipParams;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BlockTipMapper;
import com.jones.mars.repository.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockTipService extends BaseService{

    @Autowired
    private BlockTipMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse insertContent(BlockTipParam param){
        BlockTipParams params = new BlockTipParams();
        for(String blockId: param.getBlockIds()){
            params.getBlockTipParams().add(BlockTipParam.builder().blockId(blockId).spotId(param.getSpotId()).detail(param.getDetail()).build());
        }
        mapper.insert(params);
        return BaseResponse.builder().build();
    }
}


