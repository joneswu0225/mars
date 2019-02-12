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
@Builder
@ApiModel(value="企业参数")
public class EnterpriseParam extends BaseObject {
    @NotBlank(message="企业名称不能为空")
    @ApiModelProperty(value="企业名称",name="name")
    private String name;
    @ApiModelProperty(value="企业LOGO",name="imagePath")
    private String imagePath;
    @ApiModelProperty(value="企业简介",name="detail", allowEmptyValue=true)
    private String detail;
    @NotBlank(message="企业管理员ID不能为空")
    @ApiModelProperty(value="企业管理员ID",name="managerId")
    private Integer managerId;
    @ApiParam(hidden = true)
    private String basePath;
}

