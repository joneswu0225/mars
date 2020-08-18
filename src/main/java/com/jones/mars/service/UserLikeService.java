package com.jones.mars.service;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.Task;
import com.jones.mars.model.UserLike;
import com.jones.mars.model.param.TaskParam;
import com.jones.mars.model.param.UserLikeParam;
import com.jones.mars.model.query.ProjectUserQuery;
import com.jones.mars.model.query.TaskQuery;
import com.jones.mars.model.query.UserLikeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserLikeService extends BaseService{

    @Autowired
    private UserLikeMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse add(UserLikeParam param){
        param.setUserId(LoginUtil.getInstance().getUser().getId());
        mapper.insert(UserLike.builder()
                .userId(LoginUtil.getInstance().getUser().getId())
                .likeId(param.getLikeId())
                .likeType(param.getLikeType())
                .likeStatus(param.getLikeStatus()>0?UserLike.LIKE:UserLike.UNLIKE).build()
        );
        return BaseResponse.builder().build();
    }

    public BaseResponse findLikeResult(UserLikeQuery query){
        Map<String, Object> result = new HashMap<>();
        List<JSONObject> totalResult = mapper.findLikeResult(query);
        result.put("totalInfo", totalResult);
        if(LoginUtil.getInstance().getUser() != null){
            query.setUserId(LoginUtil.getInstance().getUser().getId());
            List<JSONObject> privateResult = mapper.findLikeResult(query);
            result.put("privateInfo", privateResult);
        }
        return BaseResponse.builder().data(result).build();
    }

    public BaseResponse cancel(UserLikeParam param){
        param.setUserId(LoginUtil.getInstance().getUser().getId());
        mapper.delete(param);
        return BaseResponse.builder().build();
    }

}

