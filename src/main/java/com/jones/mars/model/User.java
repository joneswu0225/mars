package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value="user")
public class User extends BaseObject {
    public static final int COMMON = 0;
    public static final int ENTMANAGER = 1;
    public static final int ADMIN = 2;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    private String mobile;
    private String nickname;
    private String departmentName;
    private String sgname;
    private String password;
    private String passwordOld;
    private String openid;
    private Long userId;
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
    private List<EnterpriseUser> enterprises;
    private List<Block> blocks;
    private List<Role> roleList;
}

