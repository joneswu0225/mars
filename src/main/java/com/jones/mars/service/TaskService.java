package com.jones.mars.service;

import com.jones.mars.config.LoginUser;
import com.jones.mars.model.Project;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.Task;
import com.jones.mars.model.param.TaskParam;
import com.jones.mars.model.query.ProjectUserQuery;
import com.jones.mars.model.query.TaskQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.ProjectMapper;
import com.jones.mars.repository.ProjectUserMapper;
import com.jones.mars.repository.TaskMapper;
import com.jones.mars.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService extends BaseService{

    @Autowired
    private TaskMapper mapper;

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse add(TaskParam param){
        List<ProjectUser> projectUserList = projectUserMapper.findList(ProjectUserQuery.builder().projectId(param.getProjectId()).build());
        if(projectUserList.size() > 0) {
            Task task = mapper.findMaxVersionTask(TaskQuery.builder().type(param.getType()).projectId(param.getProjectId()).build());
            if(task == null || task.getCurrentFlg()==Task.OLD_TASK){
                param.setVersion(task==null ? 0 : task.getVersion() + 1);
            } else {
                param.setVersion(task.getVersion());
            }
            param.setCurrentFlg(Task.CURRENT_TASK);
            param.setStatus(Task.CREATING);
            param.setUserIds(projectUserList.stream().map(p -> p.getUserId()).collect(Collectors.toList()));
            param.setCreateBy(LoginUtil.getInstance().getUser().getId());
            param.setUpdateBy(LoginUtil.getInstance().getUser().getId());
            mapper.insert(param);
        }
        return BaseResponse.builder().build();
    }

}

