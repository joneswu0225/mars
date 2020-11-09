package com.jones.mars.repository;

import com.jones.mars.model.EnterpriseUser;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.RolePermission;
import com.jones.mars.model.query.RolePermissionQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMapper {
    void insert(Object param);
    void delete(Object param);
    void deleteByRoleId(Integer roleId);
    RolePermission findOne(Integer permissionId);
    List<RolePermission> findAll(RolePermissionQuery query);
    List<EnterpriseUser> findGrantedUserByClassId(RolePermissionQuery query);
    List<ProjectUser> findGrantedUserInfoList(RolePermissionQuery query);
    Integer findGrantedUserInfoCount(RolePermissionQuery query);
    List<ProjectUser> findGrantedUserInfoAll(RolePermissionQuery query);
}
