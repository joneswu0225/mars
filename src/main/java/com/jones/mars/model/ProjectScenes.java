package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.ProjectScene;
import com.jones.mars.model.param.ProjectSceneParam;
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
@TableName("project_scene")
public class ProjectScenes {
    private List<ProjectScene> projectSceneList = new ArrayList<>();

    public ProjectScenes(ProjectSceneParam param){
        for(String sceneId : param.getSceneIds()){
            projectSceneList.add(ProjectScene.builder().projectId(param.getProjectId()).sceneId(sceneId).build());
        }
    }
}

