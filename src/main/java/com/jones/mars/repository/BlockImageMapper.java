package com.jones.mars.repository;

import com.jones.mars.model.BlockImage;
import com.jones.mars.model.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockImageMapper extends BaseMapper<BlockImage> {
    List<Object> findAllName(Query query);
}
