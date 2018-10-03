package com.jones.mars.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProjectPartner {
  public static final int PARTNER_MANAGER = 1;
  public static final int PARTNER_NORMAL = 0;
  private Integer id;
  private Integer projectId;
  private Integer userId;
  private Integer managerFlg = PARTNER_NORMAL;
}

