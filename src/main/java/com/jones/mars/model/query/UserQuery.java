package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="用户查询参数")
@Builder
public class UserQuery extends Query {
    @ApiModelProperty(value="姓名",name="sgname")
    private String sgname;
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
    @ApiModelProperty(value="密码",name="password")
    private String password;
    @ApiModelProperty(value="验证码",name="verifyCode")
    private String verifyCode;
    @ApiModelProperty(value="用户类型",name="userType")
    private Integer userType;
}

