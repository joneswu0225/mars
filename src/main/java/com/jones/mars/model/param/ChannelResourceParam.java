package com.jones.mars.model.param;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelResourceParam extends BaseObject {
    @ApiModelProperty(value="企业名称",name="company")
    private String company;
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private String enterpriseId;
    @ApiModelProperty(value="描述",name="detail")
    private String detail;
    @ApiModelProperty(value="来源方验证接口",name="authUrl")
    private String authUrl;
    @ApiModelProperty(value="验证通过后跳转链接",name="redirectUrl")
    private String redirectUrl;
    @ApiModelProperty(value="校验信息json",name="validateContent")
    private String validateContent;
    @ApiModelProperty(value="对应的登录用户ID",name="userId")
    private String userId;
}

