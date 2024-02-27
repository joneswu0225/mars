package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="场景类型参数")
@NoArgsConstructor
@AllArgsConstructor
public class BlockSceneTypeParam extends BaseObject {
    @NotBlank(message = "场景类型名称不能为空")
    @ApiModelProperty(value="场景类型名称",name="name")
    private String name;
    @ApiModelProperty(value="场景类型简介",name="detail")
    private String detail;
    @NotNull(message = "模块ID不能为空")
    @ApiModelProperty(value="模块ID称",name="blockId")
    private String blockId;
    @ApiModelProperty(value="关联场景类型ID",name="relSceneTypeId")
    private String relSceneTypeId;
    @ApiParam(hidden = true)
    private Integer seq;
}

