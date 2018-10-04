package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="模块查询参数")
@Builder
public class BlockQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @ApiModelProperty(value="模块名称",name="name")
    private String name;
    @ApiModelProperty(value="模块状态",name="staus")
    private Integer staus;
    @ApiParam(hidden = true)
    private Integer userId;
    @ApiParam(hidden = true)
    private Integer plateformFlg;
}
