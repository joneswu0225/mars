package com.jones.mars.service;

import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.query.ProjectUserQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.ProjectUserMapper;
import com.jones.mars.util.Page;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Log
@Service
public class ProjectUserService extends BaseService {
    @Autowired
    private ProjectUserMapper mapper;

    @Override
    public CommonMapper getMapper() {
        return this.mapper;
    }

    public BaseResponse findPage(ProjectUserQuery query){
        BaseResponse response = super.findByPage(query);
        List<ProjectUser> projectUsers = ((Page<ProjectUser>) response.getData()).getContent();
        fillDepartmentInfo(projectUsers);
        return response;
    }

    public BaseResponse findAll(ProjectUserQuery query){
        List<ProjectUser> projectUsers = mapper.findAll(query);
        fillDepartmentInfo(projectUsers);
        return BaseResponse.builder().data(projectUsers).build();
    }

    public List<ProjectUser> findAllData(ProjectUserQuery query){
        List<ProjectUser> projectUsers = mapper.findAll(query);
        fillDepartmentInfo(projectUsers);
        return projectUsers;
    }

    public void fillDepartmentInfo(List<ProjectUser> projectUsers){
        Iterator<ProjectUser> it = projectUsers.iterator();
        ProjectUser firstUser = null;
        while(it.hasNext()){
            ProjectUser user = it.next();
            if(firstUser != null && firstUser.getUserId() == user.getUserId()){
                firstUser.setDepartmentName(firstUser.getDepartmentName() + "," + user.getDepartmentName());
                it.remove();
            } else {
                firstUser = user;
            }
        }
    }
}
