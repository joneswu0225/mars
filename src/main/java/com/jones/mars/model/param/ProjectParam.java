package com.jones.mars.model.param;

import com.jones.mars.model.Project;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ApiModel(value="项目参数")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectParam extends BaseObject {
    @NotBlank(message = "项目名称不能为空")
    @ApiModelProperty(value="项目名称",name="name")
    private String name;
    @NotBlank(message = "项目简介不能为空")
    @ApiModelProperty(value="项目简介",name="detail")
    private String detail;
    @ApiModelProperty(value="项目图片",name="imageUrl")
    private String imageUrl;
    @ApiModelProperty(value="驳回原因",name="reason")
    private String reason;
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private String blockId;
    @NotNull(message = "一级分类ID不能为空")
    @ApiModelProperty(value="一级分类ID",name="moduleId")
    private String moduleId;
    @NotNull(message = "二级分类ID不能为空")
    @ApiModelProperty(value="二级分类ID",name="classId")
    private String classId;
    @NotNull(message = "所属企业ID不能为空")
    @ApiModelProperty(value="所属企业ID",name="oriEnterpriseId")
    private String oriEnterpriseId;
    @ApiModelProperty(value="项目共建人ID列表",name="userIds")
    private List<String> userIds = new ArrayList<>();
    @ApiParam(hidden = true)
    private Integer status = Project.CREATING;
    @ApiParam(hidden = true)
    private Date publishDate;
    @ApiModelProperty(value="是否定制项目",name="customFlg")
    private Integer customFlg;
    @ApiModelProperty(value="是否公开项目",name="publicFlg")
    private Integer publicFlg;
    @ApiModelProperty(value="是否强制更新",name="force")
    private Integer force;
    @ApiParam(hidden = true)
    private String creatorId;

}

