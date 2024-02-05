package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockTourSpotParam extends BaseObject {
    @NotNull
    @ApiModelProperty(value="类型",name="type")
    private String type;
    @ApiModelProperty(value="横坐标",name="ath")
    private Float ath;
    @ApiModelProperty(value="纵坐标",name="atv")
    private Float atv;
    @ApiModelProperty(value="视角",name="fov")
    private Float fov;
    @ApiModelProperty(value="内容",name="detail")
    private String detail;
    @ApiModelProperty(value="标题",name="title")
    private String title;
    @ApiModelProperty(value="目标场景code",name="acode")
    private String acode;
    @ApiModelProperty(value="源场景code",name="scode")
    private String scode;
    @NotNull
    @ApiModelProperty(value="模块ID",name="blockId")
    private Long blockId;
    @ApiParam(hidden = true)
    private Long id;
    @ApiParam(hidden = true)
    private Integer seq;
}

