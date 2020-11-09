package com.jones.mars.model.query;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectUserQuery extends Query {
  @ApiParam(hidden = true)
  private Integer projectId;
  @ApiParam(hidden = true)
  private Integer userId;
  @ApiParam(hidden = true)
  private List<Integer> projectIds;
  @ApiParam(hidden = true)
  private Integer managerFlg;
}

