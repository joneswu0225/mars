package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BlockExamineContentSeqParam {
    @NotNull
    @ApiModelProperty(value="船舶检查ID",name="examineId")
    private Integer examineId;
    @NotEmpty
    @ApiModelProperty(value="排序后ID列表",name="blockExamineContentIds")
    private List<Integer> blockExamineContentIds;
}

