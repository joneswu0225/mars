package com.jones.mars.model.param;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role")
public class UserRoleParam extends BaseObject {
    @ApiParam(hidden = true)
    private String roleId;
    @NotEmpty
    @ApiModelProperty(value="用户ID",name="userIds")
    private List<String> userIds = new ArrayList<>();
}

