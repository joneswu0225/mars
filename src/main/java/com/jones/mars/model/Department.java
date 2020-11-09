package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Department extends BaseObject {
    private String name;
    private Integer enterpriseId;
    private Integer parentId;
    private String parentName;
    private Integer managerId;
    private Integer userCount;
    private String managerName;
    private String managerEmail;
    private String managerMobile;
    private String managerDescription;
    private List<DepartmentUser> userList = new ArrayList<>();
    private Integer operatorId;
    private Date updateTime;
    private Date createTime;
}

