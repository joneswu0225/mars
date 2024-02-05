package com.jones.mars.repository;

import com.jones.mars.model.Block;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockMapper extends CommonMapper<Block> {
    List<Object> findAllName(Query query);
    Block findOneByCode(String code);
    List<Block> findBlockModule(Query query);
    List<Block> findBlockUserPermission(Query query);
    List<Block> findUserBlock(Query query);
}
