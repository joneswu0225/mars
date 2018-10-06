package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel(value="企业入驻参数")
public class CompanyJoinUpdateParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiModelProperty(value="状态，1：需要进一步处理，2：处理完成",name="status")
    private Integer status;
    @ApiModelProperty(value="备注",name="remark")
    private String remark;
}
