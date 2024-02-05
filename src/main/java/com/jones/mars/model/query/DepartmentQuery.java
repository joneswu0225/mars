package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="部门查询参数")
public class DepartmentQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Long enterpriseId;
    @ApiModelProperty(value="部门名称",name="name")
    private String name;
    @ApiModelProperty(value="部门ID",name="departmentId")
    private Long departmentId;
}
