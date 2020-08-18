package com.jones.mars.model.param;

import com.jones.mars.model.constant.CommentType;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
@ApiModel(value="评论参数")
public class CommentParam extends BaseObject {
    @ApiModelProperty(value="评论内容",name="content")
    private String content;
    @ApiModelProperty(value="评论类型",name="type")
    private CommentType type;
    @ApiModelProperty(value="评论关联ID",name="relatedId")
    private Integer relatedId;
    @ApiParam(hidden = true)
    private Integer userId;
}

