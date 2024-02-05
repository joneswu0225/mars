package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.ProjectScene;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.param.ProjectUserParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("project_user")
public class ProjectUsers {
    private List<ProjectUser> projectUsertList = new ArrayList<>();

    public ProjectUsers(ProjectUserParam param){
        for(Long userId : param.getUserIds()){
            projectUsertList.add(ProjectUser.builder().projectId(param.getProjectId()).userId(userId).build());
        }
    }
}

