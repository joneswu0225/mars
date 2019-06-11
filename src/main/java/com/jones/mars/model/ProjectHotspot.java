package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectHotspot extends BaseObject {
    private Integer seq;
    private Integer hotspotId;
    private Integer projectId;
}

