package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(value="系统参数")
public class AppConstParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiModelProperty(value="参数名称",name="name")
    private String name;
    @ApiModelProperty(value="参数值",name="value")
    private String value;
    @ApiModelProperty(value="参数ID列表(顺序按照ID顺序)",name="appConstIds")
    private List<Integer> appConstIds;
}

