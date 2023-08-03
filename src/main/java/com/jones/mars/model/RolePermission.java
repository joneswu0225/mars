package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission extends BaseObject {
  public static final int VIEW = 0;
  public static final int CREATE = 1;

  private Integer roleId;
  private Integer userId;
  private Integer moduleId;
  private Integer classId;
  private String className;
  private String moduleName;
  private Integer operation;
  private Date updateTime;
  private Date createTime;
}

