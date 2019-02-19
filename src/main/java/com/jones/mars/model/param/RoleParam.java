package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value="角色参数")
public class RoleParam extends BaseObject {
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value="角色名称",name="name")
    private String name;
}

