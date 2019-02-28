package com.jones.mars.model.query;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectUserQuery {
  @ApiParam(hidden = true)
  private Integer projectId;
  @ApiParam(hidden = true)
  private Integer managerFlg;
}

