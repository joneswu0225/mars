package com.jones.mars.repository;

import com.jones.mars.mapper.StyleMapper;
import com.jones.mars.model.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StyleRepository {

    @Autowired
    private StyleMapper styleMapper;

    public List<Style> findAll() {
        return this.styleMapper.findAll();
    }
}

