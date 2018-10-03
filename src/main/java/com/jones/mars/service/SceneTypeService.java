package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.SceneTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneTypeService extends BaseService {
    @Autowired
    private SceneTypeMapper mapper;
    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }


}
