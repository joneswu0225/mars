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
public class BlockExamineContentSeqParam {
    @NotNull
    @ApiModelProperty(value="船舶检查ID",name="examineId")
    private Long examineId;
    @NotEmpty
    @ApiModelProperty(value="排序后ID列表",name="blockExamineContentIds")
    private List<Long> blockExamineContentIds;
}

