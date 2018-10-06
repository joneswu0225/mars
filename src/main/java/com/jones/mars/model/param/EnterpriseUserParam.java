package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EnterpriseUserParam {
    @ApiParam(hidden = true)
    private Integer enterpriseId;
    @ApiModelProperty(value="用户ID",name="userIds")
    private List<Integer> userIds;
}

