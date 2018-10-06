package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(value="模块平面图参数")
public class BlockImageParam {

    @ApiParam(hidden = true)
    private Integer id;
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @ApiModelProperty(value="模块名称",name="detail")
    private String detail;
    @ApiModelProperty(value="模块平面图名称",name="name")
    private String name;
    @ApiModelProperty(value="模块平面图路径",name="path")
    private String path;
}

