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
@ApiModel(value="系統常量查询参数")
@Builder
public class AppConstQuery extends Query {
    @ApiModelProperty(value="常量名",name="name")
    private String name;
    @ApiModelProperty(value="常量名",name="nameLike")
    private String nameLike;
}
