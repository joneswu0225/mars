package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("role")
public class Role extends BaseObject {
  private String name;
  private String image;
  private Long enterpriseId;
  private String enterpriseName;
  private String enterpriseAvatar;
  private Long blockId;
  private String blockName;
  private String blockImageUrl;
  private Date updateTime;
  private Date createTime;
  private List<RolePermission> permissionList = new ArrayList<>();
  private List<User> userList = new ArrayList<>();
}

