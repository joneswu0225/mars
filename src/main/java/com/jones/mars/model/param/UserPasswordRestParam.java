package com.jones.mars.model.param;

import com.jones.mars.support.ValidMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="重置密码参数")
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordRestParam {
    @ValidMobile
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value="密码",name="password")
    private String password;
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value="验证码",name="verifyCode")
    private String verifyCode;
}

