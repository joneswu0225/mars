package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="模块类型参数")
@NoArgsConstructor
@AllArgsConstructor
public class BlockTypeParam extends BaseObject {
    @NotBlank(message = "模块类型名称不能为空")
    @ApiModelProperty(value="模块类型名称",name="name")
    private String name;
    @ApiModelProperty(value="模块类型简介",name="detail")
    private String detail;
    @NotNull(message = "企业D不能为空")
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private String enterpriseId;
    @ApiParam(hidden = true)
    private Integer seq;
}

