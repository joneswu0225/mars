package com.jones.mars.repository;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.UserLike;
import com.jones.mars.model.param.UserLikeParam;
import com.jones.mars.model.query.UserLikeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeMapper extends BaseMapper<UserLike> {
    void insert(UserLikeParam userLike);
    List<JSONObject> findLikeResult(UserLikeQuery query);
    void delete(UserLikeParam userLike);
}
