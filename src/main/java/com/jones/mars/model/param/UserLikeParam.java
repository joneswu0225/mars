package com.jones.mars.model.param;

import com.jones.mars.model.constant.LikeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@ApiModel(value="点赞参数")
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeParam{
    @NotBlank(message = "点赞目标的ID不能为空")
    @ApiModelProperty(value="点赞目标的ID",name="likeId")
    private String likeId;
    @ApiParam(hidden = true)
    @ApiModelProperty(value="用戶ID",name="userId")
    private String userId;
    @NotBlank(message = "点赞目标的类型不能为空")
    @ApiModelProperty(value="点赞目标的类型",name="likeType")
    private LikeType likeType;
    @ApiModelProperty(value="点赞状态，喜欢1，不喜欢0",name="likeType")
    private Integer likeStatus;
}

