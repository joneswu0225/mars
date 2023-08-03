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
@ApiModel(value="船舶检查参数")
public class BlockExamineContentQuery extends Query {
    @ApiModelProperty(value="检查ID",name="examineId")
    private Integer examineId;
    @ApiModelProperty(value="类型",name="type")
    private String type;
}
