package com.jones.mars.service;

import com.jones.mars.constant.MessageInfo;
import com.jones.mars.model.Message;
import com.jones.mars.model.Messages;
import com.jones.mars.model.query.MessageQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.MessageMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Service
public class MessageService extends BaseService{

    @Autowired
    private MessageMapper mapper;
    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    /**
     * 更新通知状态
     * @return
     */
    public BaseResponse updateStatus(String messageId, int status){
        Message message = Message.builder().status(status).build();
        message.setId(messageId);
        mapper.update(message);
        return BaseResponse.builder().build();
    }

    /**
     * 未读消息条数
     * @param userId
     * @return
     */
    public BaseResponse findUnreadCount(String userId){
        Integer count = mapper.findCount(MessageQuery.builder().receiver(userId).status(Message.STATUS_UNREAD).build());
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return BaseResponse.builder().data(result).build();
    }

    private void sendMessage(String title, String messageType, List<String> receivers, String content){
        if(receivers.size() > 0) {
            log.info(String.format("发送消息给： %s， 标题： %s， 内容：%s", String.join(",", receivers.stream().map(p->p.toString()).collect(Collectors.toList())), title, content));
            Messages messages = new Messages();
            for(String userId : receivers){
                messages.getMessageList().add(Message.builder().title(title).content(content).messageType(messageType).receiver(userId).build());
            }
            mapper.insert(messages);
        }
    }

    private void sendMessage(MessageInfo info, List<String> receivers, String content){
        sendMessage(info.title, info.messageType, receivers, content);
    }

    public void sendInvitedToEnterprise(String enterpriseName, String receiver){
        MessageInfo info = MessageInfo.INVITED_TO_ENTERPRISE;
        sendMessage(info, Arrays.asList(receiver), String.format(info.content, enterpriseName));
    }
    public void sendAddToProject(String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.ADD_TO_PROJECT;
        sendMessage(info, receivers, String.format(info.content, projectName));
    }
    public void sendAddToProjectManager(String projectName, String receiver){
        MessageInfo info = MessageInfo.ADD_TO_PROJECT_MANAGER;
        sendMessage(info, Arrays.asList(receiver), String.format(info.content, projectName));
    }
    public void sendModifyProject(String sgname, String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.MODIFY_PROJECT;
        sendMessage(info, receivers, String.format(info.content, sgname, projectName));
    }
    public void sendSubmitVerifyProject(String sgname, String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.SUBMIT_VERIFY_PROJECT;
        sendMessage(info, receivers, String.format(info.content, sgname, projectName));
    }
    public void sendVerifyPassProject(String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.VERIFY_PASS_PROJECT;
        sendMessage(info, receivers, String.format(info.content, projectName));
    }
    public void sendVerifyFailProject(String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.VERIFY_FAIL_PROJECT;
        sendMessage(info, receivers, String.format(info.content, projectName));
    }
    public void sendTaskExpiredAdmin(String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.TASK_EXPIRED_ADMIN;
        sendMessage(info, receivers, String.format(info.content, projectName));
    }
    public void sendTaskExpiredWorker(String projectName, List<String> receivers){
        MessageInfo info = MessageInfo.TASK_EXPIRED_WORKER;
        sendMessage(info, receivers, String.format(info.content, projectName));
    }
    public void sendTaskProjectModify(String projectName, String expireDate, List<String> receivers){
        MessageInfo info = MessageInfo.TASK_PROJECT_MODIFY;
        sendMessage(info, receivers, String.format(info.content, projectName, expireDate));
    }
    public void sendTaskProjectTrainning(String projectName, String expireDate, List<String> receivers){
        MessageInfo info = MessageInfo.TASK_PROJECT_TRAINNING;
        sendMessage(info, receivers, String.format(info.content, projectName, expireDate));
    }
}

