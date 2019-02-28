package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ApiModel(value="场景类型查询参数")
public class BlockSceneTypeQuery extends Query {
    @NotBlank(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
}

