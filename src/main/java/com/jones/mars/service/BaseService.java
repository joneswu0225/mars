package com.jones.mars.service;

import com.google.gson.JsonObject;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseObject;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.Page;
import org.springframework.integration.support.MapBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aliyuncs.http.FormatType.JSON;

/**
 * Created by jones on 18-9-2.
 */
public abstract class BaseService<T> {
    public abstract BaseMapper getMapper();

    public Page<T> findPage(Query query) {
        int count = getMapper().findCount(query);
        List data = getMapper().findList(query);
        return new Page<T>(query, count, data);
    }

    /**
     * 新增
     * @param param
     * @return
     */
    public BaseResponse add(BaseObject param){
        getMapper().insert(param);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", param.getId());
        return BaseResponse.builder().data(map).build();
    }

    /**
     * 更新
     * @param param
     * @return
     */
    public BaseResponse update(BaseObject param){
        getMapper().update(param);
        return BaseResponse.builder().build();
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    public BaseResponse findById(Integer id){
        Object item = getMapper().findOne(id);
        return BaseResponse.builder().data(item).build();
    }

    /**
     * 获取列表
     * @param query
     * @return
     */
    public BaseResponse findByPage(Query query){
        Page list = findPage(query);
        return BaseResponse.builder().data(list).build();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public BaseResponse delete(Integer id){
        getMapper().delete(id);
        return BaseResponse.builder().build();
    }

}
