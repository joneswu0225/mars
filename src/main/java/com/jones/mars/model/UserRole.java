package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends BaseObject {
    private Date updateTime;
    private Date createTime;
    private Integer roleId;
    private Integer userId;
}

