package com.jones.mars.model.param;

import com.jones.mars.support.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="微信更新密码参数", description = "")
@NoArgsConstructor
@AllArgsConstructor
public class UserWXUpdatePasswordParam {
    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
    @NotBlank(message = "openid不能为空")
    @ApiModelProperty(value="openid",name="openid")
    private String openid;
    @ValidPassword
    @ApiModelProperty(value="密码",name="password")
    private String password;
}

