package com.jones.mars.model;

import com.jones.mars.model.constant.UserType;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {
    private Integer id;
    private String mobileLike;
    private String mobile;
    private String sgname;
    private String password;
    private UserType userType;
    private String verifyCode;
    private String uniqueCodeLike;
    private String uniqueCode;
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
    private String namecard;
    private String description;


    public Map<String, Object> getInfo(){
        Map<String, Object> userInfo = new HashMap<>();
        return userInfo;
    }

}

