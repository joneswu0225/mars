package com.jones.mars.repository;

import com.jones.mars.mapper.UserMapper;
import com.jones.mars.model.Query;
import com.jones.mars.model.User;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    public Page<User> findByPage(Query<User> query) {
        Integer count = this.userMapper.findCount(query);
        return new Page(query, count, this.userMapper.findList(query));
    }

    public User findUser(User user) {
        return this.userMapper.findOne(user);
    }

    public List<User> findAll() {
        return this.userMapper.findAll();
    }
}

