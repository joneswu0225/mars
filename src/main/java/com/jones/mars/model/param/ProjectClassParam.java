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
@ApiModel(value="二级分类参数")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectClassParam extends BaseObject {
    @NotNull(message = "一级分类ID不能为空")
    @ApiModelProperty(value="一级分类ID",name="moduleId")
    private Integer moduleId;
    @NotBlank(message = "二级分类名称不能为空")
    @ApiModelProperty(value="二级分类名称",name="name")
    private String name;
}

