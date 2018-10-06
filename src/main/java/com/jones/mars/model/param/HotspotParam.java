package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class HotspotParam {
    @ApiParam(hidden = true)
    private Integer id;
    @ApiModelProperty(value="热点code",name="code")
    private String code;
    @ApiModelProperty(value="热点标题",name="title")
    private String title;
    @ApiModelProperty(value="热点名称",name="name")
    private String name;
    @ApiModelProperty(value="热点坐标-x",name="locationX;")
    private Float locationX;
    @ApiModelProperty(value="热点坐标-y",name="locationY")
    private Float locationY;
    @ApiModelProperty(value="是否为热点模板",name="baseFlg")
    private Integer baseFlg;
    @ApiModelProperty(value="热点模板ID",name="bhotspotId")
    private Integer bhotspotId;
    @ApiModelProperty(value="场景ID",name="sceneId")
    private Integer sceneId;
}

