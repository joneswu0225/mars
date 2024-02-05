package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUserParam extends BaseObject {
    @ApiParam(hidden = true)
    private Long departmentId;
    @ApiParam(hidden = true)
    private Long enterpriseId;
    @ApiParam(hidden = true)
    private Long userId;
    @NotEmpty
    @ApiModelProperty(value="用户ID",name="userIds")
    private List<Long> userIds;
}

