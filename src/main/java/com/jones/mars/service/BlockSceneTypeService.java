package com.jones.mars.service;

import com.jones.mars.model.BlockImage;
import com.jones.mars.model.BlockSceneType;
import com.jones.mars.model.query.BlockSceneTypeQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockImageMapper;
import com.jones.mars.repository.BlockSceneTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockSceneTypeService extends BaseService{

    @Autowired
    private BlockSceneTypeMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Integer blockId){
        List<BlockSceneType> list = mapper.findAllName(BlockSceneTypeQuery.builder().blockId(blockId).build());
        return BaseResponse.builder().data(list).build();
    }
    public BaseResponse findSceneTypeProjectScene(Integer blockId){
        List<BlockSceneType> list = mapper.findSceneTypeProjectScene(BlockSceneTypeQuery.builder().blockId(blockId).build());
        return BaseResponse.builder().data(list).build();
    }

}

