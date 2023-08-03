package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import com.jones.mars.util.KrpanoUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel(value="场景切图参数")
@NoArgsConstructor
@AllArgsConstructor
public class SlicePanoParam extends BaseObject {
    @ApiModelProperty(value="场景ID",name="sceneId")
    private Integer sceneId;
    @ApiModelProperty(value="场景code",name="sceneCode")
    private String sceneCode;
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @ApiModelProperty(value="切图类型",name="panoType")
    private KrpanoUtil.PanoType panoType;
}

