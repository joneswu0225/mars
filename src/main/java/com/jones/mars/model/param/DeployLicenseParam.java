package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@ApiModel(value="Deploy License Param")
@NoArgsConstructor
@AllArgsConstructor
public class DeployLicenseParam extends BaseObject {
    @NotBlank(message="授权的应用或企业")
    @ApiModelProperty(value="授权的应用或企业",name="name")
    private String name;
    @ApiModelProperty(value="detail",name="detail")
    private String detail;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="开始时间",name="startTime")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="结束时间",name="expireTime")
    private Date expireTime;
    @NotBlank(message="磁盘序列号")
    @ApiModelProperty(value="磁盘序列号",name="diskSeries")
    private String diskSeries;
    @NotBlank(message="目录")
    @ApiModelProperty(value="目录",name="directory")
    private String directory;
}

