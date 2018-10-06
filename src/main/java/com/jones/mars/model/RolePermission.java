package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class RolePermission {
  public static final int VIEW = 0;
  public static final int CREATE = 1;

  private Integer id;
  private Integer roleId;
  private Integer classId;
  private Integer operation;
  private Date updateTime;
  private Date createTime;
}

