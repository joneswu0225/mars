package com.jones.mars.service;

import com.jones.mars.model.Department;
import com.jones.mars.model.param.DepartmentParam;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.DepartmentMapper;
import com.jones.mars.repository.DepartmentUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(DepartmentParam param){
        mapper.insert(param);
        param.setDepartmentId(param.getId());
        if(!CollectionUtils.isEmpty(param.getUserIds())){
            departmentUserMapper.insert(param);
        }
        return BaseResponse.builder().build();
    }


    public BaseResponse addDepartmentUser(DepartmentUserParam param){
        departmentUserMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse removeDepartmentUser(DepartmentUserParam param){
        departmentUserMapper.delete(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse findOne(Integer departmentId){
        Department department = mapper.findOne(departmentId);
        department.setUserList(departmentUserMapper.findDepartmentUser(departmentId));
        return BaseResponse.builder().data(department).build();
    }



}

