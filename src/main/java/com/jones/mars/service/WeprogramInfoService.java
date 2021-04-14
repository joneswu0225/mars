package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.WeprogramInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeprogramInfoService extends BaseService{

    @Autowired
    private WeprogramInfoMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }



}

