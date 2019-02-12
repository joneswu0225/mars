package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="人员部门查询参数")
public class EnterpriseUserQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @ApiModelProperty(value="员工名称",name="sgname")
    private String sgname;
    @ApiModelProperty(value="部门ID",name="departmentId")
    private Integer departmentId;
}
