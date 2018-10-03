package com.jones.mars.service;

import com.jones.mars.model.Message;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        mapper.update(Message.builder().id(messageId).status(status).build());
        return BaseResponse.builder().build();
    }

    /**
     * 未读消息条数
     * @param userId
     * @return
     */
    public BaseResponse findUnreadCount(Integer userId){
        Integer count = mapper.findCount(new Query(Message.builder().receiver(userId).status(Message.STATUS_UNREAD).build()));
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return BaseResponse.builder().data(result).build();
    }

}

