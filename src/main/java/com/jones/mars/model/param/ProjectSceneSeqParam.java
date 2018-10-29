package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectSceneSeqParam {
    @NotNull
    @ApiModelProperty(value="项目ID",name="projectId")
    private Integer projectId;
    @NotEmpty
    @ApiModelProperty(value="场景ID列表",name="sceneIds")
    private List<Integer> sceneIds;
}

