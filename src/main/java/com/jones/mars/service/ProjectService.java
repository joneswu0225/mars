package com.jones.mars.service;

import com.jones.mars.model.BlockProject;
import com.jones.mars.model.Project;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.BlockProjectMapper;
import com.jones.mars.repository.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService extends BaseService {

    @Autowired
    private ProjectMapper mapper;
    @Autowired
    private BlockProjectMapper blockProjectMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(ProjectParam param){
        Project project = Project.projectBuilder(param).build();
        mapper.insert(project);
        System.out.println(project.getId());
        BlockProject blockProject = BlockProject.builder().blockId(param.getBlockId()).projectId(project.getId()).build();
        blockProjectMapper.insert(blockProject);
        return BaseResponse.builder().build();
    }

    public BaseResponse allName(Query query){
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

}

