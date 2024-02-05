package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("project_user")
public class ProjectUser extends BaseObject {
  public static final int PROJECT_MANAGER = 1;
  public static final int PROJECT_NORMAL = 0;
  private Long projectId;
  private Long userId;
  private String sgname;
  private String mobile;
  private String avatar;
  private String email;
  private String description;
  private String departmentName;
  private Integer managerFlg = PROJECT_NORMAL;
}

