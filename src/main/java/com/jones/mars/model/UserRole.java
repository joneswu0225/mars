package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserRole {
    private Date updateTime;
    private Date createTime;
    private Integer roleId;
    private Integer userId;
    private Integer id;
}

