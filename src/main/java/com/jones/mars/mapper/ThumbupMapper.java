package com.jones.mars.mapper;

import com.jones.mars.model.Query;
import com.jones.mars.model.Thumbup;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbupMapper {
    Integer findCount(Query paramQuery);

    void insert(Thumbup paramThumbup);

    void delete(Thumbup paramThumbup);
}

