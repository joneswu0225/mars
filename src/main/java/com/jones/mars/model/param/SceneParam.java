package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="场景参数")
public class SceneParam extends BaseObject {
    @ApiModelProperty(value="场景名称",name="name")
    private String name;
    @ApiModelProperty(value="场景编码",name="code")
    private String code;
    @ApiModelProperty(value="场景简介",name="detail")
    private String detail;
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
//    @NotNull(message = "场景类型ID不能为空")
//    @ApiModelProperty(value="场景类型ID",name="sceneTypeId")
//    private Integer sceneTypeId;
    @ApiModelProperty(value="场景全览图ID",name="imageId")
    private Integer imageId;
    @ApiModelProperty(value="全览图-x",name="locationX")
    private Float locationX;
    @ApiModelProperty(value="全览图-y",name="locationY")
    private Float locationY;

}

