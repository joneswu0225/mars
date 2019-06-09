package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class RolePermission extends BaseObject {
  public static final int VIEW = 0;
  public static final int CREATE = 1;

  private Integer roleId;
  private Integer userId;
  private Integer userSgname;
  private Integer moduleId;
  private String moduleName;
  private Integer classId;
  private String className;
  private Integer operation;
  private Date updateTime;
  private Date createTime;
}

