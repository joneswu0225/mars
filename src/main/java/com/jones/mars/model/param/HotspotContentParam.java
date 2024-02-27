package com.jones.mars.model.param;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("hotspot_content")
public class HotspotContentParam {
    @NotNull
    @ApiModelProperty(value="类型",name="type")
    private String type;
    @ApiModelProperty(value="额外信息",name="extra")
    private String extra;
    @ApiModelProperty(value="内容",name="content")
    private String content;
    @ApiModelProperty(value="标题",name="title")
    private String title;
    @NotNull
    @ApiModelProperty(value="热点ID",name="hotspotId")
    private String hotspotId;
    @ApiParam(hidden = true)
    private String id;
    @ApiParam(hidden = true)
    private Integer seq;
}

