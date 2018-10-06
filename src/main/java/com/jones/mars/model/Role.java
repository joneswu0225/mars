package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Role{
  private Integer id;
  private String name;
  private Integer enterpriseId;
  private String enterpriseName;
  private String enterpriseAvatar;
  private Integer blockId;
  private Integer blockName;
  private Date updateTime;
  private Date createTime;
  private List<RolePermission> permissionList = new ArrayList<>();
  private List<User> userList = new ArrayList<>();
}

