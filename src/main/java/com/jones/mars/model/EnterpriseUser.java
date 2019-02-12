package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EnterpriseUser extends BaseObject {
    private Date updateTime;
    private Date createTime;
    private Integer userId;
    private String userSgname;
    private Integer enterpriseId;
    private List<Department> departmentList = new ArrayList<>();
}

