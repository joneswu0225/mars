package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="用户状态参数")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileParam {
    @ApiModelProperty(value="姓名",name="sgname")
    private String sgname;
    @ApiModelProperty(value="昵称",name="nickname")
    private String nickname;
    @ApiModelProperty(value="职业",name="job")
    private String job;
    @ApiModelProperty(value="城市",name="city")
    private String city;
    @ApiModelProperty(value="省份",name="province")
    private String province;
    @ApiModelProperty(value="地址",name="address")
    private String address;
    @ApiModelProperty(value="生日",name="birthday")
    private String birthday;
    @ApiModelProperty(value="头像",name="avatar")
    private String avatar;
    @ApiModelProperty(value="邮箱",name="email")
    private String email;
    @ApiModelProperty(value="名片",name="namecard")
    private String namecard;
    @ApiModelProperty(value="个人简介",name="description")
    private String description;
    @ApiParam(hidden = true)
    private String userId;
}

