package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="一级分类参数")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModuleParam extends BaseObject {
    @NotBlank(message = "一级分类名称不能为空")
    @ApiModelProperty(value="一级分类名称",name="name")
    private String name;
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID称",name="blockId")
    private String blockId;
}

