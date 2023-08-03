package com.jones.mars.model;

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
public class Role extends BaseObject {
  private String name;
  private String image;
  private Integer enterpriseId;
  private String enterpriseName;
  private String enterpriseAvatar;
  private Integer blockId;
  private String blockName;
  private Date updateTime;
  private Date createTime;
  private List<RolePermission> permissionList = new ArrayList<>();
  private List<User> userList = new ArrayList<>();
}

