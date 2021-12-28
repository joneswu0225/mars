package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel(value="模块参数")
public class BlockParam extends BaseObject {
    @NotBlank(message = "模块名称不能为空")
    @ApiModelProperty(value="模块名称",name="name")
    private String name;
    @NotBlank(message = "公司ID")
    @ApiModelProperty(value="公司ID",name="enterpriseId")
    private Integer enterpriseId;
//    @ApiModelProperty(value="是否为平台内部模块，1：是，0：否",name="plateformFlg")
//    private Integer plateformFlg;
    @ApiModelProperty(value="模块简介",name="detail")
    private String detail;
    @ApiModelProperty(value="模块图标",name="icon")
    private String icon;
    @ApiModelProperty(value="模块code",name="code")
    private String code;
    @ApiModelProperty(value="模块封面",name="imageUrl")
    private String imageUrl;
    @ApiModelProperty(value="全景文件路径",name="panoPath")
    private String panoPath;
    @ApiModelProperty(value="模块状态，0：配备，1：运行",name="detail")
    private Integer status;
    @ApiParam(hidden = true)
    private Integer operatorId;
}

