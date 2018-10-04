package com.jones.mars.model.param;

import com.jones.mars.model.RolePermission;
import com.jones.mars.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value="权限参数")
public class RoleParam {
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @NotBlank(message = "权限名称不能为空")
    @ApiModelProperty(value="权限名称",name="name")
    private String name;
    @ApiModelProperty(value="权限列表（classId, operation）",name="permissionList")
    private List<RolePermission> permissionList;
    @ApiModelProperty(value="被授权用户id",name="userIds")
    private List<Integer> userIds;
}

