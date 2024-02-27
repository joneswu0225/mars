package com.jones.mars.model.query;

import com.jones.mars.model.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="首页项目查询参数")
public class HomeProjectQuery extends Query {
    @ApiModelProperty(value="模块ID",name="blockId")
    private String blockId;
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private String enterpriseId;
    @ApiModelProperty(value="来源企业ID",name="oriEnterpriseId")
    private String oriEnterpriseId;
    @ApiParam(hidden = true)
    private Integer plateform_flg = CommonConstant.PLATEFROM;
    @ApiParam(hidden = true)
    private String name;
    @ApiParam(hidden = true)
    private String moduleId;
    @ApiParam(hidden = true)
    private String classId;
}
