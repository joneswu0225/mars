package com.jones.mars.model;

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
public class ProjectHotspot extends BaseObject {
    private Integer seq;
    private Integer hotspotId;
    private List<Integer> hotspotIds;
    private Integer projectId;
}

