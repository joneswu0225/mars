package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value="部门查询参数")
public class DepartmentQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @ApiModelProperty(value="部门名称",name="name")
    private String name;
    @ApiModelProperty(value="部门ID",name="departmentId")
    private Integer departmentId;
}
