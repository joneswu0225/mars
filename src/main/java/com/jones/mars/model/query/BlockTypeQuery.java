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
@ApiModel(value="场景类型查询参数")
public class BlockTypeQuery extends Query {
    @NotBlank(message = "企业ID不能为空")
    @ApiModelProperty(value="企业ID",name="enterpriseId")
    private Long enterpriseId;
}

