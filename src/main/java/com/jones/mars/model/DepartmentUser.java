package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("department_user")
public class DepartmentUser extends BaseObject {
    private Date updateTime;
    private Date createTime;
    private String userId;
    private String userSgname;
    private String userEmail;
    private String userMobile;
    private String userDescription;
    private String departmentId;
    private String enterpriseId;
}

