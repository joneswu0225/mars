package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class User extends BaseObject {
    public static final int COMMON = 0;
    public static final int ENTMANAGER = 1;
    public static final int ADMIN = 2;

    private String mobile;
    private String sgname;
    private String password;
    private Integer userType = COMMON;
    private String verifyCode;
    private String ip;
    private Date lastLoginTime;
    private Date updateTime;
    private Date createTime;
    private Integer status;
    private String job;
    private String city;
    private String province;
    private String address;
    private String birthday;
    private String avatar;
    private String email;
    private String namecard;
    private String description;
    private String auth;
    private List<Enterprise> enterprises;
    private List<Role> roleList;
}

