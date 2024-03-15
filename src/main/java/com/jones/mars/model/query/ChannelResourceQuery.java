package com.jones.mars.model.query;

import com.jones.mars.model.constant.CommentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="渠道来源查询参数")
public class ChannelResourceQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private String enterpriseId;
    @ApiModelProperty(value="企业名称",name="company")
    private String company;
    @ApiModelProperty(value="校验token",name="token")
    private String token;
}
