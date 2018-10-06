package com.jones.mars.service;

import com.jones.mars.model.param.DepartmentParam;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.DepartmentMapper;
import com.jones.mars.repository.DepartmentUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentMapper mapper;
    @Autowired
    private DepartmentUserMapper departmentUserMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse allName(Query query){
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse addDepartmentUser(DepartmentUserParam param){
        departmentUserMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse removeDepartmentUser(Integer departmentUserId){
        departmentUserMapper.delete(departmentUserId);
        return BaseResponse.builder().build();
    }

    public BaseResponse findOne(Integer departmentUserId){

        departmentUserMapper.delete(departmentUserId);
        return BaseResponse.builder().build();
    }



}

