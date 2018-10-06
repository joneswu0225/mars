package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Department {
    private Integer id;
    private String name;
    private Integer enterpriseId;
    private String level;
    private Integer parentId;
    private String parentName;
    private Integer managerId;
    private String managerName;
    private List<DepartmentUser> userList = new ArrayList<>();
    private Integer operatorId;
    private Date updateTime;
    private Date createTime;
}

