package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="项目查询参数")
public class ProjectQuery extends Query {
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Integer enterpriseId;
    @ApiModelProperty(value="来源企业ID",name="oriEnterpriseId")
    private Integer oriEnterpriseId;
    @ApiModelProperty(value="项目名称",name="name")
    private String name;
    @ApiModelProperty(value="一级分类ID",name="moduleId")
    private Integer moduleId;
    @ApiModelProperty(value="二级分类ID",name="classId")
    private Integer classId;
    @ApiModelProperty(value="隐藏下架，1：隐藏，0：显示",name="filterDownShelf")
    private Integer filterDownShelf;
}
