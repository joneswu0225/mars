package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@ApiModel(value="系统参数")
@NoArgsConstructor
@AllArgsConstructor
public class AppConstParam {
    @ApiParam(hidden = true)
    private String id;
    @ApiModelProperty(value="参数名称",name="name")
    private String name;
    @ApiModelProperty(value="参数值",name="value")
    private String value;
    @ApiModelProperty(value="参数ID列表(顺序按照ID顺序)",name="appConstIds")
    private List<String> appConstIds;
}

