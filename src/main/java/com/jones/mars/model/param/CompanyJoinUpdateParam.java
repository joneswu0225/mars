package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="企业入驻参数")
public class CompanyJoinUpdateParam {
    @ApiModelProperty(value="备注",name="remark")
    private String remark;
}
