package com.jones.mars.repository;

import com.jones.mars.mapper.CatalogMapper;
import com.jones.mars.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatalogRepository {

    @Autowired
    private CatalogMapper catalogMapper;

    public List<Catalog> findAll() {
        return this.catalogMapper.findAll();
    }
}

