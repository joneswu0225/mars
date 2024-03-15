package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("channel_resource")
public class ChannelResource extends BaseObject {
    private String id;
    private String company;
    private String enterpriseId;
    private String detail;
    private String authUrl;
    private String redirectUrl;
    private String validateContent;
    private String userId;
    private String token;
    private Date tokenUpdateTime;
    private Date createTime;
}

