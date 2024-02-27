package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("department")
public class Department extends BaseObject {
    private String name;
    private String enterpriseId;
    private String parentId;
    private String parentName;
    private Integer managerId;
    private Integer userCount;
    private String managerName;
    private String managerEmail;
    private String managerMobile;
    private String managerDescription;
    private List<DepartmentUser> userList = new ArrayList<>();
    private String operatorId;
    private Date updateTime;
    private Date createTime;
}

