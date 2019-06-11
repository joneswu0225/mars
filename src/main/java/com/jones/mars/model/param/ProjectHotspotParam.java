package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(value="项目热点参数")
public class ProjectHotspotParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiParam(hidden = true)
    private Integer projectId;
    @ApiModelProperty(value="热点ID列表(热点顺序按照热点ID顺序排列)",name="hotspotIds")
    private List<Integer> hotspotIds;
}

