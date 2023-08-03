package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseUser extends BaseObject {
    private Date updateTime;
    private Date createTime;
    private Integer userId;
    private String userSgname;
    private String avatar;
    private String mobile;
    private String description;
    private String email;
    private Integer enterpriseId;
    private List<Department> departmentList = new ArrayList<>();
}

