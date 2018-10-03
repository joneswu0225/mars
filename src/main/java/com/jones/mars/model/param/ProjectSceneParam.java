package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(value="项目场景参数")
public class ProjectSceneParam {
    @NotNull(message = "项目ID不能为空")
    @ApiModelProperty(value="项目ID",name="projectId")
    private Integer projectId;
    @NotNull(message = "场景ID不能为空")
    @ApiModelProperty(value="场景ID",name="sceneId")
    private Integer sceneId;
    @NotNull(message = "场景顺序不能为空")
    @ApiModelProperty(value="场景顺序",name="seq")
    private Integer seq;
}

