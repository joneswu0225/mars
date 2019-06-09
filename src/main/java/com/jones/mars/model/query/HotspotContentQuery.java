package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@ApiModel(value="热点内容查询参数")
public class HotspotContentQuery extends Query {
    @ApiModelProperty(value="场景ID",name="hotspotId")
    private Integer hotspotId;
    @ApiModelProperty(value="热点内容大分类",name="module")
    private String module;
    @ApiModelProperty(value="热点内容类型",name="type")
    private String type;
}
