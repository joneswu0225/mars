package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value="部门人员查询参数")
public class DepartmentUserQuery extends Query {
    @ApiParam(hidden = true)
    private Integer departmentId;
}
