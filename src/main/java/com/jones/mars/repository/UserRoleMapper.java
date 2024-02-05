package com.jones.mars.repository;

import com.jones.mars.model.EnterpriseUser;
import com.jones.mars.model.UserRole;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.model.query.UserRoleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper extends CommonMapper<UserRole> {

    void deleteByEnterpriseUser(EnterpriseUser enterpriseUser);
}
