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
public class SceneSeqParam {
    @NotNull
    @ApiModelProperty(value="模块ID",name="blockId")
    private Integer blockId;
    @NotEmpty
    @ApiModelProperty(value="场景ID列表",name="sceneIds")
    private List<Integer> sceneIds;
}

