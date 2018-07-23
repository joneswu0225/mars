package com.jones.mars.repository;

import com.jones.mars.mapper.ThumbupMapper;
import com.jones.mars.model.Query;
import com.jones.mars.model.Thumbup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ThumbupRepository {

    @Autowired
    private ThumbupMapper thumbupMapper;

    public void save(Thumbup thumbup) {
        this.thumbupMapper.insert(thumbup);
    }

    public void delete(Thumbup thumbup) {
        this.thumbupMapper.delete(thumbup);
    }

    public Integer findCount(Query<Thumbup> query) {
        return this.thumbupMapper.findCount(query);
    }
}

