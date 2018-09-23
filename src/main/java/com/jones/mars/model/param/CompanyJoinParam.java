package com.jones.mars.model.param;

import com.jones.mars.support.ValidMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel(value="企业入驻参数")
public class CompanyJoinParam {
    @NotBlank(message = "公司名称不能为空")
    @ApiModelProperty(value="公司名称",name="companyName")
    private String companyName;
    @NotBlank(message = "联系人不能为空")
    @ApiModelProperty(value="联系人",name="contactor")
    private String contactor;
    @ApiModelProperty(value="职位",name="title")
    private String title;
    @ApiModelProperty(value="邮箱",name="email")
    private String email;
    @ValidMobile
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
}
