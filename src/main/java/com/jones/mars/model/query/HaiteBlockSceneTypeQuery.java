package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ApiModel(value="海特场景类型查询参数")
public class HaiteBlockSceneTypeQuery {
    @NotBlank(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Long block_id;

    @NotBlank(message = "token不能为空")
    @ApiModelProperty(value="token",name="token")
    private String token;
}

