package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class EnterpriseUserParam {
    @ApiParam(hidden = true)
    private Integer enterpriseId;
    @ApiModelProperty(value="用户ID",name="userId")
    private Integer userId;
    @ApiModelProperty(value="部门ID",name="departmentId")
    private Integer departmentId;
}

