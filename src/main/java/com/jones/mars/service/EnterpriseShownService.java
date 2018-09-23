package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.EnterpriseShownMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseShownService extends BaseService{
    @Autowired
    private EnterpriseShownMapper mapper;

    @Override
    public BaseMapper getMapper() {
        return mapper;
    }

}

