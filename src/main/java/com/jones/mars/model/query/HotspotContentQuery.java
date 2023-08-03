package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="热点内容查询参数")
public class HotspotContentQuery extends Query {
    @ApiModelProperty(value="场景ID",name="hotspotId")
    private Integer hotspotId;
    @ApiModelProperty(value="热点内容类型",name="type")
    private String type;
}
