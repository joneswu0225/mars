package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(value="模块漫游热点参数")
public class BlockHotspotParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiParam(hidden = true)
    private Integer blockId;
    @ApiParam(hidden = true)
    private Integer seq;
    @ApiModelProperty(value="源热点ID",name="srcHotspotId")
    private Integer srcHotspotId;
    @ApiModelProperty(value="目标热点ID",name="destHotspotId")
    private Integer destHotspotId;
    @ApiModelProperty(value="漫游热点关联ID列表(顺序按照关联ID顺序排列)",name="blockHotspotIds")
    private List<Integer> blockHotspotIds;
}

