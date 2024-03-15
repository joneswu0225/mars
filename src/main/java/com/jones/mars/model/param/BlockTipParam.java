package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockTipParam extends BaseObject {
    @ApiModelProperty(value="漫游热点ID",name="spotId")
    private String spotId;
    @ApiModelProperty(value="标签内容",name="detail")
    private String detail;
    @ApiModelProperty(value="模块IDs",name="blockIds")
    private List<String> blockIds;
    @ApiParam(hidden = true)
    private String blockId;



}

