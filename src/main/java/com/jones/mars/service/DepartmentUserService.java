package com.jones.mars.service;

import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.CommentMapper;
import com.jones.mars.repository.DepartmentUserMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class DepartmentUserService extends BaseService {
    @Autowired
    private DepartmentUserMapper mapper;


    @Override
    public CommonMapper getMapper() {
        return this.mapper;
    }
}
