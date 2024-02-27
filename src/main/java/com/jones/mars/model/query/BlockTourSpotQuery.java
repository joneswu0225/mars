package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="漫游热点参数")
public class BlockTourSpotQuery extends Query {
    @ApiModelProperty(value="模块ID",name="blockId")
    private String blockId;
    @ApiModelProperty(value="模块IDs",name="blockIds")
    private List<String> blockIds;
    @ApiModelProperty(value="类型",name="type")
    private String type;
}
