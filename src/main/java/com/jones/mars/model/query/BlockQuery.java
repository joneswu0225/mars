package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="模块查询参数")
@Builder
public class BlockQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @ApiModelProperty(value="模块名称",name="name")
    private String name;
    @ApiModelProperty(value="模块code",name="code")
    private String code;
    @ApiModelProperty(value="一级分类名称",name="moduleName")
    private String moduleName;
    @ApiModelProperty(value="模块状态",name="status")
    private Integer status;
    @ApiModelProperty(value="操作类型，0：企业项目，1：我的项目",name="operation")
    private Integer operation;
    @ApiParam(hidden = true)
    private Integer userId;
    @ApiParam(hidden = true)
    private List<Integer> enterpriseIds;
}
