package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class UserRole extends BaseObject {
    private Date updateTime;
    private Date createTime;
    private Integer roleId;
    private Integer userId;
}

