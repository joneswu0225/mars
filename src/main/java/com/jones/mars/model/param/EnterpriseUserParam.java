package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class EnterpriseUserParam extends BaseObject {
    @ApiParam(hidden = true)
    private Integer enterpriseId;
    @ApiParam(hidden = true)
    private Integer roleId;
    @ApiParam(hidden = true)
    private List<Integer> userIds;
    @ApiModelProperty(value="用户ID",name="userId")
    private Integer userId;
    @ApiModelProperty(value="部门ID",name="departmentId")
    private Integer departmentId;
}

