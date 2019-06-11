package com.jones.mars.service;

import com.jones.mars.repository.AppConstMapper;
import com.jones.mars.repository.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppConstService extends BaseService{

    @Autowired
    private AppConstMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

}

