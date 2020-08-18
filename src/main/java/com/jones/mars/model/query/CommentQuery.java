package com.jones.mars.model.query;

import com.jones.mars.model.constant.CommentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="评论查询参数")
public class CommentQuery extends Query {
    @ApiModelProperty(value="评论类型",name="type")
    private CommentType type;
    @ApiModelProperty(value="关联id",name="relatedId")
    private Integer relatedId;
}
