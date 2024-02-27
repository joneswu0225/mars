package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="权限查询参数")
@Builder
public class RolePermissionQuery extends Query{
    @ApiModelProperty(value="二级分类ID",name="classId")
    private String classId;
    @ApiModelProperty(value="操作类型，0：VIEW，1：CREATE   ",name="operation")
    private Integer operation;
    @ApiParam(hidden = true)
    private String userId;
    @ApiParam(hidden = true)
    private String projectId;
}
