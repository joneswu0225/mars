package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("message")
public class Message extends BaseObject {
    public static final String DEFAULT_MESSAGE_TYPE = "PROJECT";
    private String title;
    private String content;
    private Date createTime;
    private Integer status;
    private String receiver;
    private String poster = "0";
    private String messageType = DEFAULT_MESSAGE_TYPE;
    private List<String> receiverList;

    public static final int STATUS_UNREAD = 0;
    public static final int STATUS_READ = 1;
    public static final int STATUS_DELETE = 2;
}

