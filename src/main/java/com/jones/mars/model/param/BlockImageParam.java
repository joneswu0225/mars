package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class BlockImageParam {
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

