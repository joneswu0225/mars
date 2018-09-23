package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value="入驻企业（品牌）参数")
public class EnterpriseShownParam {
    @ApiModelProperty(value="品牌名称",name="name")
    private String name;
    @ApiModelProperty(value="品牌图片",name="imageUrl")
    private String imageUrl;
    @ApiModelProperty(value="关联企业",name="enterpriseId", allowEmptyValue=true)
    private Integer enterpriseId;
}

