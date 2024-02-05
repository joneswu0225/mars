package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@ApiModel(value="项目场景参数")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSceneParam {
    @ApiParam(hidden = true)
    private Long id;
    @ApiParam(hidden = true)
    private Integer seq;
    @ApiParam(hidden = true)
    private Long projectId;
    @ApiModelProperty(value="场景ID",name="sceneId")
    private Long sceneId;
    @ApiModelProperty(value="场景ID列表(场景顺序按照场景ID顺序排列)",name="sceneIds")
    private List<Long> sceneIds;
}

