package com.jones.mars.repository;

import com.jones.mars.model.EnterpriseUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseUserMapper extends CommonMapper<EnterpriseUser> {
    List<EnterpriseUser> findEnterpriseUser(Long enterpriseId);
}
