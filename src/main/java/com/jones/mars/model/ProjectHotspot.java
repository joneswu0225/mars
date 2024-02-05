package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("project_hotspot")
public class ProjectHotspot extends BaseObject {
    private Integer seq;
    private Long hotspotId;
    private List<Long> hotspotIds;
    private Long projectId;
}

