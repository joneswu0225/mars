package com.jones.mars.service;

import com.jones.mars.model.query.RoleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.RoleMapper;
import com.jones.mars.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService{

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public BaseMapper getMapper(){
        return this.roleMapper;
    }

    public BaseResponse findGrantedBlocks(RoleQuery query){
        Integer userId = LoginUtil.getInstance().getUser().getId();
        return BaseResponse.builder().data(roleMapper.findGrantedBlock(RoleQuery.builder().userId(userId).build())).build();
    }




}

