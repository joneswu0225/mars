package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DepartmentUser {
    private Date updateTime;
    private Date createTime;
    private Integer userId;
    private String userSgname;
    private Integer departmentId;
    private Integer id;
}

