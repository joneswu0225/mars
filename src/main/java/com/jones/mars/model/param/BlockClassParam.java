package com.jones.mars.model.param;

import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockClassParam extends BaseObject {
    @ApiModelProperty(value="一级分类ID",name="moduleId")
    private Integer moduleId;
    @ApiModelProperty(value="二级分类ID列表(热点顺序按照热点ID顺序排列)",name="classIds")
    private List<Integer> classIds;
}

