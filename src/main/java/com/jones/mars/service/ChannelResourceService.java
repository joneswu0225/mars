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
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ChannelResource channelResource = mapper.findOne(channelResourceId);
        mapper.update(ChannelResource.builder().id(channelResourceId).token(token).tokenUpdateTime(new Date()).build());
        Map<String, String> result = new HashMap<>();
        result.put("token", token);
        result.put("authUrl", channelResource.getAuthUrl());
        return BaseResponse.builder().data(result).build();
    }

    public BaseResponse validateAndLogin(ChannelResourceAuthParam param){
        long expireTimeMillis = 1000*60;
        ChannelResource channelResource = mapper.findOne(param.getId());
        // validate token
        if(!param.getToken().equals(channelResource.getToken()) || (System.currentTimeMillis() - channelResource.getTokenUpdateTime().getTime() > expireTimeMillis)){
            return BaseResponse.builder().code(ErrorCode.CHANNEL_TOKEN_VALIDATE_FAILED).build();
        }
        String userId = channelResource.getDefaultUserId();
        // validate content
        try {
            JSONObject data = JSONObject.parseObject(param.getValidateContent());
            JSONObject dbData = JSONObject.parseObject(channelResource.getValidateContent());
            for(String field: dbData.keySet()){
                if(!data.containsKey(field) || !dbData.getString(field).equals(data.getString(field))){
                    return BaseResponse.builder().code(ErrorCode.CHANNEL_CONTENT_VALIDATE_FAILED).build();
                }
            }
            if(StringUtils.hasLength(channelResource.getRoleField()) && StringUtils.hasLength(channelResource.getRoleMapping())){
                String dataRole = data.getString(channelResource.getRoleField());
                JSONObject roles = JSONObject.parseObject(channelResource.getRoleMapping());
                for(String key: roles.keySet()){
                    if(dataRole.contains(key)){
                        userId = roles.getString(key);
                        break;
                    }
                }
            }
        } catch (Exception e){
            log.info("validate channel token failed channelResourceId: " + param.getId());
        }
        if(!StringUtils.hasLength(userId)){
            return BaseResponse.builder().code(ErrorCode.CHANNEL_CONTENT_ROLE_MAPPING_FAILED).build();
        }
        // login
        BaseResponse resp = userService.innerLogin(userId, CommonConstant.APP_SOURCE_PC);
        Map<String, Object> result = ((Map<String, Object>)resp.getData());
        result.put("redirectUrl", channelResource.getRedirectUrl());
        result.put("enterpriseId", channelResource.getEnterpriseId());
        return resp;
    }
}
