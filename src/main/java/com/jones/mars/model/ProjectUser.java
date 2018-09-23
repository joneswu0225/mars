package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectUser {
  private Integer id;
  private Integer projectId;
  private Integer userId;
  private Integer managerFlg = 0;
}

