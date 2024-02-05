package com.jones.mars.service;

import com.jones.mars.model.param.BlockClassParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.BlockClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectClassService extends BaseService{

    @Autowired
    private BlockClassMapper mapper;
    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse findAll(Query query){
        return BaseResponse.builder().data(mapper.findAll(query)).build();
    }

    public BaseResponse updateBlockClassSeq(BlockClassParam param){
        mapper.updateBlockClassSeq(param);
        return BaseResponse.builder().build();
    }
}


