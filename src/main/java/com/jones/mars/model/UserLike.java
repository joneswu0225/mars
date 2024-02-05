package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.constant.LikeType;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user_like")
public class UserLike extends BaseObject {
    public static final String LIKE = "LIKE";
    public static final String UNLIKE = "UNLIKE";

    private Long id;
    private Long userId;
    private Long likeId;
    private LikeType likeType;
    private String likeStatus;
    private Date createTime;
    private Date updateTime;

}

