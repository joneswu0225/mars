package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="权限查询参数")
public class RoleQuery extends Query {
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Long enterpriseId;
    @ApiModelProperty(value="模块ID",name="blockId")
    private Long blockId;
}

