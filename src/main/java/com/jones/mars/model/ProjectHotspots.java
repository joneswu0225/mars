package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.ProjectHotspot;
import com.jones.mars.model.param.ProjectHotspotParam;
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
@TableName("project_hotspot")
public class ProjectHotspots {
    private List<ProjectHotspot> projectHotspotList = new ArrayList<>();

    public ProjectHotspots(ProjectHotspotParam param){
        for(Long hotspotId : param.getHotspotIds()){
            projectHotspotList.add(ProjectHotspot.builder().projectId(param.getProjectId()).hotspotId(hotspotId).build());
        }
    }
}

