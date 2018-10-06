package com.jones.mars.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.jones.mars.model.RolePermission;
import com.jones.mars.model.param.RoleParam;
import com.jones.mars.model.param.RolePermissionParam;
import com.jones.mars.model.param.UserRoleParam;
import com.jones.mars.model.query.RoleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.RoleMapper;
import com.jones.mars.repository.RolePermissionMapper;
import com.jones.mars.repository.UserRoleMapper;
import com.jones.mars.util.LoginUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService extends BaseService{

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public BaseMapper getMapper(){
        return this.roleMapper;
    }


    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(RoleParam param){
        roleMapper.insert(param);
        if(!CollectionUtils.isEmpty(param.getPermissionList())){
            param.getPermissionList().forEach(p->{
                p.setRoleId(param.getId());
                rolePermissionMapper.insert(p);
            });
        }
        if(!CollectionUtils.isEmpty(param.getUserIds())){
            userRoleMapper.insert(UserRoleParam.builder().roleId(param.getId()).userIds(param.getUserIds()));
        }
        return BaseResponse.builder().build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse delete(Integer roleId){
        userRoleMapper.delete(UserRoleParam.builder().roleId(roleId).build());
        rolePermissionMapper.deleteByRoleId(roleId);
        roleMapper.delete(roleId);
        return BaseResponse.builder().build();
    }

    public BaseResponse addPermission(RolePermissionParam param){
        rolePermissionMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse removePermission(Integer permissionId){
        rolePermissionMapper.delete(permissionId);
        return BaseResponse.builder().build();
    }

    public BaseResponse addUser(UserRoleParam param){
        userRoleMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse removeUser(UserRoleParam param){
        rolePermissionMapper.delete(param);
        return BaseResponse.builder().build();
    }
}

