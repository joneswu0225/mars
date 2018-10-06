package com.jones.mars.repository;

import com.jones.mars.model.EnterpriseUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseUserMapper extends BaseMapper<EnterpriseUser> {
    List<EnterpriseUser> findEnterpriseUser(Integer enterpriseId);
}
