package com.jones.mars.model;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private Role role;

}

