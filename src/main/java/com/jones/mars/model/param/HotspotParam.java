package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class HotspotParam extends BaseObject {
    @ApiModelProperty(value="热点code",name="code")
    private String code;
    @NotBlank(message = "热点标题不能为空")
    @ApiModelProperty(value="热点标题",name="title")
    private String title;
    @NotNull(message = "热点坐标-x不能为空")
    @ApiModelProperty(value="热点坐标-x",name="locationX")
    private Float locationX;
    @NotNull(message = "热点坐标-y不能为空")
    @ApiModelProperty(value="热点坐标-y",name="locationY")
    private Float locationY;
    @NotNull(message = "热点坐标-fov不能为空")
    @ApiModelProperty(value="热点坐标-fov",name="locationFov")
    private Float locationFov;
    @NotNull(message = "场景ID不能为空")
    @ApiModelProperty(value="场景ID",name="sceneId")
    private Integer sceneId;
    @NotNull(message = "项目ID不能为空")
    @ApiModelProperty(value="项目ID",name="projectId")
    private Integer projectId;
    @ApiModelProperty(value="热点图标",name="icon")
    private String icon;
    @ApiModelProperty(value="热点类型",name="type")
    private String type;
}

