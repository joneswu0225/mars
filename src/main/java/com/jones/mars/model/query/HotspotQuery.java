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
    @ApiModelProperty(hidden = true)
    private Set<Integer> sceneIds;
}
