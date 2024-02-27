package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockTypeSeqParam {
    @NotNull
    @ApiModelProperty(value="enterpriseId",name="enterpriseId")
    private String enterpriseId;
    @NotEmpty
    @ApiModelProperty(value="模块类型ID列表",name="blockTypeIds")
    private List<String> blockTypeIds;
}

