package com.jones.mars.repository;

import com.jones.mars.model.Block;
import com.jones.mars.model.Role;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper {
    List<Block> findGrantedBlock(Query query);
    List<Role> findRolePermissionList(Query query);
}

