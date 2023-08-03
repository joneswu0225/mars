package com.jones.mars.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value="火山上传参数")
@NoArgsConstructor
@AllArgsConstructor
public class HuoshanSwapfaceParam {
    @NotBlank(message = "图片名称")
    @ApiModelProperty(value="模块ID",name="imageName")
    private String imageName;
    @NotBlank(message = "图片base64")
    @ApiModelProperty(value="企业ID",name="imageBase64")
    private String imageBase64;
    @ApiModelProperty(value="模板名称",name="templateName")
    @NotBlank(message = "模板名称")
    private String templateName;
    @ApiModelProperty(value="模板base64",name="templateBase64")
    private String templateBase64;
    @ApiModelProperty(value="模板上传相对路径",name="templatePath")
    private String templatePath;
}

