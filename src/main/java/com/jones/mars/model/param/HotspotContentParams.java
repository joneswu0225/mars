package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotspotContentParams {
    @ApiModelProperty(value="热点内容列表",name="hotspotContentList")
    private List<HotspotContentParam> hotspotContentList = new ArrayList<>();
}

