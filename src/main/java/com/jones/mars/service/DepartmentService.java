package com.jones.mars.service;

import com.jones.mars.model.Department;
import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.param.DepartmentParam;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.DepartmentMapper;
import com.jones.mars.repository.DepartmentUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        // TODO return id
        return BaseResponse.builder().build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse update(DepartmentParam param){
        List<Integer> users = departmentUserMapper.findDepartmentUser(param.getDepartmentId()).parallelStream().map(p->p.getUserId()).collect(Collectors.toList());
        List<Integer> userIds = new ArrayList<>();
        Collections.copy(userIds, param.getUserIds());
        // 传入的去掉已有的=仍需添加的
        param.getUserIds().removeAll(users);
        // 已有的去掉传入的=不需要的，要删除
        users.removeAll(userIds);
        if(users.size()>0) {
            departmentUserMapper.deleteByDepartmentParam(DepartmentParam.builder().departmentId(param.getDepartmentId()).userIds(users));
        }
        if(param.getUserIds().size()>0){
            departmentUserMapper.insert(param);
        }
        mapper.update(param);
        return BaseResponse.builder().build();
    }


    public BaseResponse addDepartmentUser(DepartmentUserParam param){
        departmentUserMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse removeDepartmentUser(DepartmentUser param){
        departmentUserMapper.delete(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse findOne(Integer departmentId){
        Department department = mapper.findOne(departmentId);
        department.setUserList(departmentUserMapper.findDepartmentUser(departmentId));
        return BaseResponse.builder().data(department).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse delete(Integer departmentId){
        EnterpriseUserParam param = EnterpriseUserParam.builder().departmentId(departmentId).build();
        param.setId(departmentId);
        mapper.delete(param);
        departmentUserMapper.delete(param);
        return BaseResponse.builder().build();
    }



}

