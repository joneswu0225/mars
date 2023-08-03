package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseObject {
    public static final int COMMON = 0;
    public static final int ENTMANAGER = 1;
    public static final int ADMIN = 2;

    private Integer id;
    private String mobile;
    private String nickname;
    private String departmentName;
    private String sgname;
    private String password;
    private String openid;
    private String unionid;
    private Integer userType;
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
    private List<Block> blocks;
    private List<Role> roleList;
}

