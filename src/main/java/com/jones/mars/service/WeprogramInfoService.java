package com.jones.mars.service;

import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.WeprogramInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeprogramInfoService extends BaseService{

    @Autowired
    private WeprogramInfoMapper mapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }



}

