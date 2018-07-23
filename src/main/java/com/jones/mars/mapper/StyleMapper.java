package com.jones.mars.mapper;

import com.jones.mars.model.Style;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleMapper {
    List<Style> findAll();
}

