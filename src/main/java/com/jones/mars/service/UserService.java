package com.jones.mars.service;

import com.jones.mars.model.Query;
import com.jones.mars.model.User;
import com.jones.mars.repository.UserRepository;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public Page<User> findByPage(Query<User> query) {
        return this.userRepository.findByPage(query);
    }

    public User doLogin(User user) {
        return this.userRepository.findUser(user);
    }
}

