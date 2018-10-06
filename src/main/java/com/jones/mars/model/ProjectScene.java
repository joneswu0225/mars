package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectScene {
    private Integer seq;
    private Integer sceneId;
    private Integer projectId;
    private Integer id;
}

