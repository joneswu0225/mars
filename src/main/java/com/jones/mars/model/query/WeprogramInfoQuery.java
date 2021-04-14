package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="小程序配置查询参数")
@Builder
public class WeprogramInfoQuery extends Query {
    @ApiModelProperty(value="名称",name="name")
    private String name;
    @ApiModelProperty(value="名称",name="nameLike")
    private String nameLike;
}
