package com.jones.mars.service;

import com.jones.mars.model.Block;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService extends BaseService{

    @Autowired
    private BlockMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Integer enterpriseId){
        Query query = new Query(Block.builder().enterpriseId(enterpriseId).build());
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

}

