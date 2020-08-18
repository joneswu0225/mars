package com.jones.mars.model;

import com.jones.mars.model.constant.CommentType;
import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Comment extends BaseObject {
    private String content;
    private Integer userId;
    private String userName;
    private String userAvatar;
    private Date createTime;
    private CommentType type;
    private Integer relatedId;
}

