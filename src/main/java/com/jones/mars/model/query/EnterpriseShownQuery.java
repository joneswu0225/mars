package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel(value="企业入驻查询参数")
public class EnterpriseShownQuery extends Query {
    @ApiParam(hidden = true)
    private List<Integer> ids;
}
