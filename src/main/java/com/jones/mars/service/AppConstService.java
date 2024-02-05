package com.jones.mars.service;

import com.jones.mars.model.AppConst;
import com.jones.mars.model.Project;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.AppConstParam;
import com.jones.mars.model.query.AppConstQuery;
import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.AppConstMapper;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppConstService extends BaseService{

    @Autowired
    private AppConstMapper mapper;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse updateAppConstSeq(AppConstParam param){
        mapper.updateAppConstSeq(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse findRecommendProject(){
        List<AppConst> consts = mapper.findList(AppConstQuery.builder().name(AppConst.HOME_RECOMMEND_PROJECT).build());
        List<Long> ids = consts.stream().map(p-> Long.parseLong(p.getValue())).collect(Collectors.toList());//.stream().map(p-> Integer.parseInt(p.getValue())).collect(Collectors.toList());
        List<Project> projects = projectMapper.findAll(ProjectQuery.builder().ids(ids).build());
        Map<Long, Project> projectIdMap = projects.stream().collect(Collectors.toMap(Project::getId, p->p));
        List<Project> results = new ArrayList<>();
        consts.forEach(p->{
            p.setRel(projectIdMap.get(Long.parseLong(p.getValue())));
        });
        return BaseResponse.builder().data(consts).build();
    }

}

