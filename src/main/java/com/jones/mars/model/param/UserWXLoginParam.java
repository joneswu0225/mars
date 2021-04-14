package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="微信登录参数", description = "")
public class UserWXLoginParam {
    @NotNull(message = "小程序ID不能为空")
    @ApiModelProperty(value="小程序ID",name="weprogramId")
    private Integer weprogramId;
    @NotBlank(message = "加密数据不能为空")
    @ApiModelProperty(value="加密数据",name="encryptedData")
    private String encryptedData;
    @NotBlank(message = "初始向量不能为空")
    @ApiModelProperty(value="初始向量",name="iv")
    private String iv;
    @NotBlank(message = "登录凭证code不能为空")
    @ApiModelProperty(value="登录凭证",name="code")
    private String code;
}

