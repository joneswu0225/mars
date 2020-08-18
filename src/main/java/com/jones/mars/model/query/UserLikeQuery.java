package com.jones.mars.model.query;

import com.jones.mars.model.constant.LikeType;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserLikeQuery extends BaseObject {
    @NotBlank(message = "点赞目标的ID不能为空")
    @ApiModelProperty(value="点赞目标的ID",name="likeId")
    private Integer likeId;
    @ApiParam(hidden = true)
    @ApiModelProperty(value="用戶ID",name="userId")
    private Integer userId;
    @NotBlank(message = "点赞目标的类型不能为空")
    @ApiModelProperty(value="点赞目标的类型",name="likeType")
    private LikeType likeType;

}

