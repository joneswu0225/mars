package com.jones.mars.model.param;

import com.jones.mars.support.ValidMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value="用户参数")
public class UserParam {
    @ValidMobile
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
    @ApiModelProperty(value="用户类型",name="userType")
    private Integer userType;
    @ApiModelProperty(value="用户状态",name="status")
    private Integer status;
}

