package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("role_permission")
public class RolePermission extends BaseObject {
  public static final int VIEW = 0;
  public static final int CREATE = 1;

  private Long roleId;
  private Long userId;
  private Long moduleId;
  private Long classId;
  private String className;
  private String moduleName;
  private Integer operation;
  private Date updateTime;
  private Date createTime;
}

