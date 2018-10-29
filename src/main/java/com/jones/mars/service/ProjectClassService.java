package com.jones.mars.service;

import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectClassService extends BaseService{

    @Autowired
    private BlockClassMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse findAll(Query query){
        return BaseResponse.builder().data(mapper.findAll(query)).build();
    }

}

