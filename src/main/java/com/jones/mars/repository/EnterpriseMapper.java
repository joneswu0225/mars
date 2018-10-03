package com.jones.mars.repository;

import com.jones.mars.model.Enterprise;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterpriseMapper extends BaseMapper<Enterprise> {
    List<Enterprise> findAllName(Query query);
    List<Enterprise> findUserEnterprise(Query query);
}
