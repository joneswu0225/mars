package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectHotspot extends BaseObject {
    private Integer seq;
    private Integer hotspotId;
    private List<Integer> hotspotIds;
    private Integer projectId;
}

