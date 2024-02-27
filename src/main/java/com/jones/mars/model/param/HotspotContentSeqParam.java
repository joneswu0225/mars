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
public class HotspotContentSeqParam {
    @NotNull
    @ApiModelProperty(value="热点ID",name="hotspotId")
    private String hotspotId;
    @NotEmpty
    @ApiModelProperty(value="热点内容ID列表",name="hotspotContentIds")
    private List<String> hotspotContentIds;
}

