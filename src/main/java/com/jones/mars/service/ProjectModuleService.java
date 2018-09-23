package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.ProjectModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleService extends BaseService{

    @Autowired
    private ProjectModuleMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

}

