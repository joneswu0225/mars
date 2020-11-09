package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.RolePermission;
import com.jones.mars.model.User;
import com.jones.mars.model.UserRole;
import com.jones.mars.model.param.ProjectUserParam;
import com.jones.mars.model.param.RoleUserParam;
import com.jones.mars.model.param.RolePermissionParam;
import com.jones.mars.model.param.UserRoleParam;
import com.jones.mars.model.query.UserRoleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.LoginUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseService{

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Override
    public BaseMapper getMapper(){
        return this.roleMapper;
    }


    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(RoleUserParam param){
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
        List<UserRole> users = userRoleMapper.findAll(UserRoleQuery.builder().roleId(roleId).build());
        if(users.size() > 0){
            return BaseResponse.builder().code(ErrorCode.ROLE_DELETE_EXIST_USER).build();
        } else {
            userRoleMapper.delete(UserRoleParam.builder().roleId(roleId).build());
            rolePermissionMapper.deleteByRoleId(roleId);
            roleMapper.delete(roleId);
            return BaseResponse.builder().build();
        }
    }

    public BaseResponse addPermission(RolePermissionParam param){
        rolePermissionMapper.insert(param);
        return BaseResponse.builder().build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse removePermission(Integer permissionId){
        if(LoginUtil.getInstance().getUser().getUserType().equals(User.ADMIN)) {
            // 刪除只依赖该角色所成为的项目共建人
            List<ProjectUser> projectUsers = projectUserMapper.findProjectUserByRolePermission(RolePermissionParam.builder().permissionId(permissionId).build());
            if(projectUsers.size() > 0) {
                List<Integer> projectUserIds = projectUsers.stream().map(p -> p.getId()).collect(Collectors.toList());
                projectUserMapper.delete(ProjectUserParam.builder().ids(projectUserIds).build());
            }
            rolePermissionMapper.delete(permissionId);
            return BaseResponse.builder().build();
        } else {
            return BaseResponse.builder().code(ErrorCode.ROLE_PERMISSION_DELETE_NOAUTH).build();
        }
    }

    public BaseResponse addUser(UserRoleParam param){
        userRoleMapper.insert(param);
        return BaseResponse.builder().build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse removeUser(UserRoleParam param){
        // 刪除只依赖该角色所成为的项目共建人
        List<ProjectUser> projectUsers = projectUserMapper.findProjectUserByRolePermission(RolePermissionParam.builder().roleId(param.getRoleId()).userId(param.getUserId()).build());
        if(projectUsers.size() > 0) {
            List<Integer> projectUserIds = projectUsers.stream().map(p -> p.getId()).collect(Collectors.toList());
            projectUserMapper.delete(ProjectUserParam.builder().ids(projectUserIds).build());
        }
        userRoleMapper.delete(param);
        return BaseResponse.builder().build();
    }
}

