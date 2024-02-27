package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="热点查询参数")
public class HotspotQuery extends Query {
    @ApiModelProperty(value="场景ID",name="sceneId")
    private String sceneId;
    @ApiModelProperty(value="项目ID",name="projectId")
    private String projectId;
    @ApiModelProperty(hidden = true)
    private Set<String> sceneIds;
    @ApiModelProperty(hidden = true)
    private Boolean hasSceneCode;
    @ApiModelProperty(value="热点类型集合",name="types")
    private List<String> types;
    @ApiModelProperty(value="热点类型",name="type")
    private String type;
}
