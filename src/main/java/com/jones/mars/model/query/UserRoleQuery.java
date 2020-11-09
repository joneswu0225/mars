package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="角色查询参数")
@Builder
public class UserRoleQuery extends Query{
    @ApiParam(hidden = true)
    private Integer roleId;
}
