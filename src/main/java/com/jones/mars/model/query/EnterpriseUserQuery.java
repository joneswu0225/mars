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
@ApiModel(value="企业人员")
public class EnterpriseUserQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private String enterpriseId;
    @ApiModelProperty(value="员工名称",name="sgname")
    private String sgname;
    @ApiModelProperty(value="员工名称",name="userId")
    private String userId;
    @ApiModelProperty(value="部门ID",name="departmentId")
    private String departmentId;
    @ApiModelProperty(value="是否为企业管理员",name="isManager")
    private Integer isManager;
}
