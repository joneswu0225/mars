package com.jones.mars.service;

import com.jones.mars.constant.MessageInfo;
import com.jones.mars.model.Message;
import com.jones.mars.model.query.MessageQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
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
    public BaseMapper getMapper(){
        return this.mapper;
    }

    /**
     * 更新通知状态
     * @return
     */
    public BaseResponse updateStatus(Integer messageId, int status){
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
    public BaseResponse findUnreadCount(Integer userId){
        Integer count = mapper.findCount(MessageQuery.builder().receiver(userId).status(Message.STATUS_UNREAD).build());
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return BaseResponse.builder().data(result).build();
    }

    private void sendMessage(String title, String content, List<Integer> receivers){
        if(receivers.size() > 0) {
            log.info(String.format("发送消息给： %s， 标题： %s， 内容：%s", String.join(",", receivers.stream().map(p->p.toString()).collect(Collectors.toList())), title, content));
            Message message = Message.builder().title(title).content(content).receiverList(receivers).build();
            mapper.insert(message);
        }
    }

    public void sendInvitedToEnterprise(String enterpriseName, Integer receiver){
        MessageInfo info = MessageInfo.INVITED_TO_ENTERPRISE;
        sendMessage(info.title, String.format(info.content, enterpriseName), Arrays.asList(receiver));
    }
    public void sendAddToProject(String projectName, List<Integer> receivers){
        MessageInfo info = MessageInfo.ADD_TO_PROJECT;
        sendMessage(info.title, String.format(info.content, projectName), receivers);
    }
    public void sendAddToProjectManager(String projectName, Integer receiver){
        MessageInfo info = MessageInfo.ADD_TO_PROJECT_MANAGER;
        sendMessage(info.title, String.format(info.content, projectName), Arrays.asList(receiver));
    }
    public void sendModifyProject(String sgname, String projectName, List<Integer> receivers){
        MessageInfo info = MessageInfo.MODIFY_PROJECT;
        sendMessage(info.title, String.format(info.content, sgname, projectName), receivers);
    }
    public void sendSubmitVerifyProject(String sgname, String projectName, Integer receiver){
        MessageInfo info = MessageInfo.SUBMIT_VERIFY_PROJECT;
        sendMessage(info.title, String.format(info.content, sgname, projectName), Arrays.asList(receiver));
    }
    public void sendVerifyPassProject(String projectName, List<Integer> receivers){
        MessageInfo info = MessageInfo.VERIFY_PASS_PROJECT;
        sendMessage(info.title, String.format(info.content, projectName), receivers);
    }
    public void sendVerifyFailProject(String projectName, List<Integer> receivers){
        MessageInfo info = MessageInfo.VERIFY_FAIL_PROJECT;
        sendMessage(info.title, String.format(info.content, projectName), receivers);
    }
}

