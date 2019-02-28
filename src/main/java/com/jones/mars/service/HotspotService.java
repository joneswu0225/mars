package com.jones.mars.service;

import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotspotService  extends BaseService{

    @Autowired
    private HotspotMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

}


