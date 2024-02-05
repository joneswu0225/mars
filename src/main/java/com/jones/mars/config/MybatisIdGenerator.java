package com.jones.mars.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.jones.mars.util.Snowflake;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

//@Component
//public class MybatisIdGenerator implements IdentifierGenerator {
//
//    @Override
//    public Long nextId(Object entity) {
//        //可以将当前传入的class全类名来作为bizKey或者提取参数来生成bizKey进行分布式Id调用生成
////        String bizKey = entity.getClass().getName();
//        MetaObject metaObject = SystemMetaObject.forObject(entity);
//        Long tableId = getTableId(entity);
//        Long deployId = getDeployId();
//        Long id = new Snowflake(deployId, tableId).nextId();
//        metaObject.setValue("id", id);
//        return id;
//    }
//
//    private static Long getTableId(Object entity){
//        return 63l;
//    }
//    private static Long getDeployId(){
//        return 64l;
//    }
//}
