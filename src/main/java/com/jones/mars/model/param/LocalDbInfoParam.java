package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@ApiModel(value="Local DB Info Param")
@NoArgsConstructor
@AllArgsConstructor
public class LocalDbInfoParam extends BaseObject {
    @ApiModelProperty(value="deployLicense中的key",name="key")
    private String key;
    @NotBlank(message="blockID")
    @ApiModelProperty(value="blockID",name="blockId")
    private String blockId;
    @ApiModelProperty(value="导出文件名",name="fileName")
    private String fileName = "local.db";
}

