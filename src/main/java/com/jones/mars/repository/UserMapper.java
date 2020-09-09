package com.jones.mars.repository;

import com.jones.mars.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    void insertProfile(User paramUser);
    void updateProfile(Object paramUser);
    void updateByMobile(Object paramUser);
    User findOneByMobile(String mobile);
}
