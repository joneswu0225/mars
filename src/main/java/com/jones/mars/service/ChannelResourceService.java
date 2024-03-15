package com.jones.mars.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.Block;
import com.jones.mars.model.ChannelResource;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.param.ChannelResourceAuthParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.ChannelResourceMapper;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.RandomString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log
@Service
public class ChannelResourceService extends BaseService {
    @Autowired
    private ChannelResourceMapper mapper;
    @Autowired
    private UserService userService;


    @Override
    public CommonMapper getMapper() {
        return this.mapper;
    }


    public BaseResponse generateToken(String channelResourceId){
        String token = RandomString.generateVerifyCode();
        mapper.update(ChannelResource.builder().id(channelResourceId).token(token).tokenUpdateTime(new Date()).build());
        return BaseResponse.builder().data(token).build();
    }

    public BaseResponse validateAndLogin(ChannelResourceAuthParam param){
        long expireTimeMillis = 1000*60;
        ChannelResource channelResource = mapper.findOne(param.getId());
        // validate token
        if(!param.getToken().equals(channelResource.getToken()) || (System.currentTimeMillis() - channelResource.getTokenUpdateTime().getTime() > expireTimeMillis)){
            return BaseResponse.builder().code(ErrorCode.CHANNEL_TOKEN_VALIDATE_FAILED).build();
        }
        // validate content
        try {
            String[] validateFields = new String[]{"enterprise_id"};
            JSONObject data = JSONObject.parseObject(param.getValidateContent());
            JSONObject dbData = JSONObject.parseObject(channelResource.getValidateContent());
            for(String field: validateFields){
                if(!data.containsKey(field) || !dbData.getString(field).equals(data.getString(field))){
                    return BaseResponse.builder().code(ErrorCode.CHANNEL_CONTENT_VALIDATE_FAILED).build();
                }
            }
        } catch (Exception e){
            log.info("validate channel token failed channelResourceId: " + param.getId());
        }
        // login
        String userId = channelResource.getUserId();
        return userService.innerLogin(userId, CommonConstant.APP_SOURCE_PC);
    }
}
