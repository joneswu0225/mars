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
public class BlockTourSpotSeqParam {
    @NotNull
    @ApiModelProperty(value="模块ID",name="blockId")
    private String blockId;
    @NotEmpty
    @ApiModelProperty(value="排序后ID列表",name="tourSpotIds")
    private List<String> tourSpotIds;
}

