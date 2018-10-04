package com.jones.mars.service;

import com.jones.mars.model.ProjectClass;
import com.jones.mars.model.ProjectModule;
import com.jones.mars.model.query.ProjectModuleQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.ProjectClassMapper;
import com.jones.mars.repository.ProjectModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectModuleService extends BaseService{

    @Autowired
    private ProjectModuleMapper projectModuleMapper;
    @Override
    public BaseMapper getMapper(){
        return this.projectModuleMapper;
    }

    public BaseResponse findAllProjectModule(ProjectModuleQuery query){
        return BaseResponse.builder().data(projectModuleMapper.findAll(query)).build();
    }


}

