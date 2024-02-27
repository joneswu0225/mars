package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSceneSeqParam {
    @NotNull
    @ApiModelProperty(value="项目ID",name="projectId")
    private String projectId;
    @NotEmpty
    @ApiModelProperty(value="场景ID列表",name="sceneIds")
    private List<String> sceneIds;
}

