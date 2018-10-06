package com.jones.mars.repository;

import com.jones.mars.model.EnterpriseUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseUserMapper {
    void insert(Object param);
    void delete(Object param);
    List<EnterpriseUser> findEnterpriseUser(Integer enterpriseId);
}
