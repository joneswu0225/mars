package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(value="模块平面图参数")
@NoArgsConstructor
@AllArgsConstructor
public class BlockImageParam extends BaseObject {
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private String blockId;
    @ApiModelProperty(value="模块名称",name="detail")
    private String detail;
    @ApiModelProperty(value="模块平面图名称",name="name")
    private String name;
    @ApiModelProperty(value="模块平面图路径",name="path")
    private String path;
}

