package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.EnterpriseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserService extends BaseService{

    @Autowired
    private EnterpriseUserMapper mapper;
    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }


}

