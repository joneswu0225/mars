package com.jones.mars.repository;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.constant.ApplicationConst;
import org.springframework.beans.factory.annotation.Value;
import com.jones.mars.model.query.Query;
import com.jones.mars.util.Snowflake;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface CommonMapper<T> {
    List<T> findList(Query paramQuery);

    T findOne(Long id);

    int findCount(Query paramQuery);

    List<T> findAll(Query paramQuery);

    void insertRecord (Object param);

    default Long insert(Object param) {
        MetaObject metaObject = SystemMetaObject.forObject(param);
        String tableName = null;
        if(param.getClass().isAnnotationPresent(TableName.class)){
            tableName = param.getClass().getAnnotation(TableName.class).value();
        }
        for (String field : metaObject.getGetterNames()){
            if(metaObject.getValue(field) instanceof List){
                for(Object object: (List) (metaObject.getValue(field))){
                    setId(SystemMetaObject.forObject(object), ApplicationConst.generateId(tableName));
                }
            }
        }
        Long totalId = ApplicationConst.generateId(tableName);
        setId(metaObject, totalId);
        this.insertRecord(metaObject.getOriginalObject());
        return totalId;
    }

    default void insertMany(Object param){
        this.insert(param);
    }

    default void setId(MetaObject metaObject, Long id) {
        if(metaObject.hasSetter("id")){
            metaObject.setValue("id", id);
        }
    }

    void update(Object param);

    void delete(Object param);
}

