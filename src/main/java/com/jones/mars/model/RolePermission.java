package com.jones.mars.model;

import com.jones.mars.model.constant.ClassOperation;
import lombok.Data;

import java.util.Date;

@Data
public class RolePermission {
  private Integer id;
  private Integer roleId;
  private Integer classId;
  private ClassOperation operation;
  private Date updateTime;
  private Date createTime;
}

