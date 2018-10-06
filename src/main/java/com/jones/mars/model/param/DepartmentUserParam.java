package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DepartmentUserParam {
    @ApiParam(hidden = true)
    private Integer departmentId;
    @NotEmpty
    @ApiModelProperty(value="用户ID",name="userIds")
    private List<Integer> userIds;
}

