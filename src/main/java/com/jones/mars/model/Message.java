package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Message extends BaseObject {
    private String title;
    private String content;
    private Date createTime;
    private Integer status;
    private Integer receiver;

    public static final int STATUS_UNREAD = 0;
    public static final int STATUS_READ = 1;
    public static final int STATUS_DELETE = 2;
}

