package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value="权限参数")
public class RoleUserParam {
    @ApiParam(hidden = true)
    private Integer id;
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @NotNull(message = "企业ID不能为空")
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @NotBlank(message = "权限名称不能为空")
    @ApiModelProperty(value="权限名称",name="name")
    private String name;
    @ApiModelProperty(value="图片路径",name="image")
    private String image;
    @ApiModelProperty(value="权限列表（classId, operation）",name="permissionList")
    private List<RolePermissionParam> permissionList;
    @ApiModelProperty(value="被授权用户id",name="userIds")
    private List<Integer> userIds;
}

