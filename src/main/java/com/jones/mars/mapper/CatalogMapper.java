package com.jones.mars.mapper;

import com.jones.mars.model.Catalog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogMapper {
    List<Catalog> findAll();
}

