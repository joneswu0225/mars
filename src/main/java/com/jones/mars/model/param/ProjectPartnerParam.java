package com.jones.mars.model.param;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectPartnerParam {
  private Integer projectId;
  private Integer[] userIds;
  private Integer managerFlg = 0;
}

