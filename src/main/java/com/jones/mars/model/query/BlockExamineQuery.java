package com.jones.mars.model.query;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="船舶检查查询参数")
public class BlockExamineQuery extends Query {
    @ApiModelProperty(value="名称",name="name")
    private String name;
    @ApiModelProperty(value="模块ID",name="blockId")
    private Long blockId;
    @ApiModelProperty(value="父ID",name="parentId")
    private Long parentId;
    @ApiModelProperty(value="类型",name="类型")
    private String type;
    @ApiParam(hidden = true)
    private Long creatorId;
}

