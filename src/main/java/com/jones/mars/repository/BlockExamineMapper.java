package com.jones.mars.repository;

import com.jones.mars.model.Block;
import com.jones.mars.model.BlockExamine;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockExamineMapper extends CommonMapper<BlockExamine> {
    List<Object> findAllName(Query query);

}
