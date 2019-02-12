package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfile extends BaseObject {
    private String description;
    private String birthday;
    private Date createTime;
    private Date updateTime;
    private String avatar;
    private String namecard;
    private String address;
    private String city;
    private String province;
    private String email;
    private String job;
    private String mobile;
    private String sgname;
    private Integer userId;
}

