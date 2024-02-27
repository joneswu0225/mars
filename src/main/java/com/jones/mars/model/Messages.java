package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.Message;
import com.jones.mars.model.RolePermission;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("message")
public class Messages extends BaseObject {
    private List<Message> messageList = new ArrayList<>();

    public Messages(Message param){

        for(String receiver : param.getReceiverList()){
            messageList.add(Message.builder().title(param.getTitle())
                    .content(param.getContent())
                    .poster(param.getPoster())
                    .messageType(param.getMessageType())
                    .receiver(receiver)
                    .build());
        }
    }
}

