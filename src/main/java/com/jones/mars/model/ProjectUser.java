package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProjectUser {
  public static final int PROJECT_MANAGER = 1;
  public static final int PROJECT_NORMAL = 0;
  private Integer id;
  private Integer projectId;
  private Integer userId;
  private String sgname;
  private Integer managerFlg = PROJECT_NORMAL;
}

