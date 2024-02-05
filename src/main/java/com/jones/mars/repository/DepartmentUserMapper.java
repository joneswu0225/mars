package com.jones.mars.repository;

import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.EnterpriseUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentUserMapper extends CommonMapper<DepartmentUser> {
    void deleteByDepartmentParam(Object param);
    void deleteByEnterpriseUser(EnterpriseUser param);
}
