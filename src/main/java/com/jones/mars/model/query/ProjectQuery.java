package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="项目查询参数")
public class ProjectQuery extends Query {
    @ApiModelProperty(value="模块ID",name="blockId")
    private Long blockId;
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Long enterpriseId;
    @ApiModelProperty(value="来源企业ID",name="oriEnterpriseId")
    private Long oriEnterpriseId;
    @ApiModelProperty(value="项目名称",name="name")
    private String name;
    @ApiModelProperty(value="一级分类ID",name="moduleId")
    private Long moduleId;
    @ApiModelProperty(value="一级分类名称",name="moduleName")
    private Long moduleName;
    @ApiModelProperty(value="二级分类ID",name="classId")
    private Long classId;
    @ApiModelProperty(value="查询状态",name="status")
    private Integer status;
    @ApiModelProperty(value="发布状态",name="publishFlg")
    private Integer publishFlg;
    @ApiModelProperty(value="公开状态",name="publicFlg")
    private Integer publicFlg;
    @ApiModelProperty(value="隐藏下架，1：隐藏，0：显示",name="filterDownShelf")
    private Integer filterDownShelf;
    @ApiParam(hidden = true)
    private Integer plateformFlg;
    @ApiParam(hidden = true)
    private Long userId;
    @ApiParam(hidden = true)
    private List<Long> ids;
    @ApiModelProperty(value="是否可编辑，1：可编辑，我的项目，0：不可编辑企业项目",name="operation")
    private Integer operation;
}
