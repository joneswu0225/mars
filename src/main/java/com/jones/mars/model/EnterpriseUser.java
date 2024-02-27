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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("enterprise_user")
public class EnterpriseUser extends BaseObject {

    public static final int ENTERPRISE_MANAGER = 1;
    public static final int ENTERPRISE_NORMAL = 0;
    private Date updateTime;
    private Date createTime;
    private String userId;
    private String userSgname;
    private String avatar;
    private String detail;
    private String imagePath;
    private String basePath;
    private String name;
    private String mobile;
    private String description;
    private String email;
    private String enterpriseId;
    private Integer managerFlg;
    private List<Department> departmentList = new ArrayList<>();
}

