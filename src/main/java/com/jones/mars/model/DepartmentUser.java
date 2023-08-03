package com.jones.mars.model;

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
public class DepartmentUser extends BaseObject {
    private Date updateTime;
    private Date createTime;
    private Integer userId;
    private String userSgname;
    private String userEmail;
    private String userMobile;
    private String userDescription;
    private Integer departmentId;
    private Integer enterpriseId;
}

