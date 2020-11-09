package com.jones.mars.service;

import com.alibaba.fastjson.JSONObject;
import com.jones.mars.model.News;
import com.jones.mars.model.constant.LikeType;
import com.jones.mars.model.query.UserLikeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.NewsMapper;
import com.jones.mars.repository.UserLikeMapper;
import com.jones.mars.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService extends BaseService{

    @Autowired
    private NewsMapper mapper;
    @Autowired
    private UserLikeMapper userLikeMapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse findNewsDetail(Integer newsId){
        News news = mapper.findOne(newsId);
        List<JSONObject> likeResult = userLikeMapper.findLikeResult(UserLikeQuery.builder().likeType(LikeType.NEWS).likeId(newsId).build());
        Map<String, Integer> likeInfo =  new HashMap<>();
        Map<String, Integer> privateLikeInfo =  new HashMap<>();
        List<JSONObject> privateLikeResult = userLikeMapper.findLikeResult(UserLikeQuery.builder().likeType(LikeType.NEWS).likeId(newsId).userId(LoginUtil.getInstance().getUser().getId()).build());
        for(JSONObject result : likeResult){
            likeInfo.put(result.getString("likeStatus"), result.getInteger("count"));
        }
        for(JSONObject result : privateLikeResult){
            privateLikeInfo.put(result.getString("likeStatus"), result.getInteger("count"));
        }
        news.setTotalLikeInfo(likeInfo);
        news.setPrivateLikeInfo(privateLikeInfo);
        return BaseResponse.builder().data(news).build();
    }

    public BaseResponse updateTopFlg(Integer newsId, Integer topFlg) {
        mapper.update(News.builder().id(newsId).topFlg(topFlg).build());
        return BaseResponse.builder().build();
    }

    public BaseResponse updateStatus(Integer newsId, Integer status) {
        News news = News.builder().id(newsId).status(status).build();
        if(status.equals(News.STATUS_PUBLISHED)){
            news.setPublishTime(new Date());
        }
        mapper.update(news);
        return BaseResponse.builder().build();
    }

}

