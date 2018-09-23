package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends BaseService{

    @Autowired
    private NewsMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

}

