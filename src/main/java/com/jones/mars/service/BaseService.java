package com.jones.mars.service;

import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.Page;

import java.util.List;

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
    public BaseResponse add(Object param){
        getMapper().insert(param);
        return BaseResponse.builder().build();
    }

    /**
     * 更新
     * @param param
     * @return
     */
    public BaseResponse update(Object param){
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
