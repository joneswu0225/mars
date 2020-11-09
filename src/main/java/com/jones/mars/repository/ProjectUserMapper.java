package com.jones.mars.repository;

import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.RolePermission;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.model.param.RolePermissionParam;
import com.jones.mars.model.param.UserRoleParam;
import com.jones.mars.model.query.ProjectUserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserMapper extends BaseMapper<ProjectUser> {
    void deleteByEnterpriseUser(EnterpriseUserParam param);
    List<ProjectUser> findProjectUserByRolePermission(RolePermissionParam param);
}
