package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="权限查询参数")
@Builder
public class RolePermissionQuery{
    @ApiModelProperty(value="二级分类ID",name="classId")
    private Integer classId;
    @ApiModelProperty(value="操作类型，0：VIEW，1：CREATE   ",name="operation")
    private Integer operation;
    @ApiParam(hidden = true)
    private Integer userId;
}
