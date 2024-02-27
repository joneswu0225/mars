package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="模块内容查询参数")
public class BlockContentQuery extends Query {
    @ApiModelProperty(value="模块ID",name="blockId")
    private String blockId;
    @ApiModelProperty(value="模块内容类型",name="type")
    private String type;
}
