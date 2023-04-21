package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ApiModel(value="一级分类查询参数")
public class ProjectClassQuery extends Query {
    @NotBlank(message = "二级分类ID不能为空")
    @ApiModelProperty(value="二级分类ID",name="moduleId")
    private Integer moduleId;
    @NotBlank(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
}

