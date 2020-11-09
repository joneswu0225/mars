package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="新闻动态查询参数")
@Builder
public class NewsQuery extends Query {
    @ApiModelProperty(value="新闻状态",name="status")
    private Integer status;
    @ApiModelProperty(value="新闻状态",name="statusIn")
    private Integer[] statusIn;
}
