package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@ApiModel(value="项目场景参数")
public class ProjectSceneParam {
    @ApiParam(hidden = true)
    private Integer projectId;
    @ApiModelProperty(value="场景ID列表",name="sceneIds")
    private List<Integer> sceneIds;
}

