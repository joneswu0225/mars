package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="消息查询参数")
@Builder
public class MessageQuery extends Query {
    @ApiModelProperty(value="消息接收人Id",name="receiver")
    private Integer receiver;
    @ApiModelProperty(value="已读状态",name="status")
    private Integer status;
}
