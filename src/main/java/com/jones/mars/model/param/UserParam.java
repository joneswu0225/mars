package com.jones.mars.model.param;

import com.jones.mars.support.ValidMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="注册用户参数")
public class UserParam {
    @ValidMobile
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value="密码",name="password")
    private String password;
    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value="用户类型",name="userType")
    private Integer userType;
}

