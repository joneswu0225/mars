package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class UserRoleParam extends BaseObject {
    @ApiParam(hidden = true)
    private Integer roleId;
    @ApiParam(hidden = true)
    private Integer enterpriseId;
    @ApiParam(hidden = true)
    private Integer userId;
    @NotEmpty
    @ApiModelProperty(value="用户ID",name="userIds")
    private List<Integer> userIds;
}

