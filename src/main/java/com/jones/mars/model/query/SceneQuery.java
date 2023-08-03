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
@ApiModel(value="场景查询参数")
public class SceneQuery extends Query {
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @ApiModelProperty(value="项目ID",name="projectId")
    private Integer projectId;
    @ApiModelProperty(value="是否为公开的场景,0:非公开,1:公开",name="publicFlg")
    private Integer publicFlg;
    @ApiModelProperty(value="一级分类ID",name="moduleId")
    private Integer moduleId;
    @ApiModelProperty(value="二级分类ID",name="classId")
    private Integer classId;
    @ApiModelProperty(value="场景名称",name="name")
    private String name;
    @ApiModelProperty(value="场景Code",name="sceneCode")
    private String sceneCode;
    @ApiModelProperty(value="场景类型ID",name="sceneTypeId")
    private Integer sceneTypeId;
    @ApiModelProperty(value="场景切图状态",name="sliceStatus")
    private Integer sliceStatus;
}
