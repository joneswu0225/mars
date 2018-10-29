package com.jones.mars.service;

import com.jones.mars.model.query.ProjectModuleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleService extends BaseService{

    @Autowired
    private BlockModuleMapper projectModuleMapper;
    @Override
    public BaseMapper getMapper(){
        return this.projectModuleMapper;
    }

    public BaseResponse findAllProjectModule(ProjectModuleQuery query){
        return BaseResponse.builder().data(projectModuleMapper.findAll(query)).build();
    }


}

