package com.jones.mars.repository;

import com.jones.mars.model.CompanyJoin;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyJoinMapper extends BaseMapper<CompanyJoin> {
    List<Object> findAllName(Query query);
}
