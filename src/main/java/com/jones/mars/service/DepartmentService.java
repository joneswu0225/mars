package com.jones.mars.service;

import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Query query){
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

}

