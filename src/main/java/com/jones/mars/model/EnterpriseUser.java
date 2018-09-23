package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class EnterpriseUser {
    private Date updateTime;
    private Date createTime;
    private Integer userId;
    private Integer positionId;
    private Integer enterpriseId;
    private Integer id;
}

