package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="license查询参数")
public class DeployLicenseQuery extends Query {
    @ApiModelProperty(value="磁盘序列号",name="diskSeries")
    private String diskSeries;
}
