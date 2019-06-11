package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@ApiModel(value="热点查询参数")
public class HotspotQuery extends Query {
    @ApiModelProperty(value="场景ID",name="sceneId")
    private Integer sceneId;
    @ApiModelProperty(value="项目ID",name="projectId")
    private Integer projectId;
    @ApiModelProperty(hidden = true)
    private Set<Integer> sceneIds;
    @ApiModelProperty(value="热点类型集合",name="types")
    private Set<String> types;
    @ApiModelProperty(value="热点类型",name="type")
    private String type;
}
