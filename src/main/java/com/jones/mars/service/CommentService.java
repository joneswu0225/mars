package com.jones.mars.service;

import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.CommentMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class CommentService extends BaseService {
    @Autowired
    private CommentMapper mapper;


    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }
}
