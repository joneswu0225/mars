package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value="船舶检查参数")
public class BlockExamineParam extends BaseObject {
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value="名称",name="name")
    private String name;
    @NotBlank(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @ApiModelProperty(value="父ID",name="parentId")
    private Integer parentId;
    @ApiModelProperty(value="简介",name="detail")
    private String detail;
    @ApiModelProperty(value="类型",name="类型")
    private String type;
    @ApiModelProperty(value="图片URL",name="imageUrl")
    private String imageUrl;
    @ApiParam(hidden = true)
    private Integer creatorId;
    @ApiParam(hidden = true)
    private Integer operatorId;
}

