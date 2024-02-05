package com.jones.mars.service;

import com.jones.mars.model.BlockImage;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.BlockImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockImageService extends BaseService{

    @Autowired
    private BlockImageMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Long blockId){
        Query query = new Query(BlockImage.builder().blockId(blockId).build());
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

}

