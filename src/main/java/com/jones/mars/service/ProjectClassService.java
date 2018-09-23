package com.jones.mars.service;

import com.jones.mars.model.constant.ProjectClass;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.ProjectClassMapper;
import com.jones.mars.repository.ProjectModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectClassService extends BaseService{

    @Autowired
    private ProjectClassMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

}

