package com.jones.mars.model.query;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUserQuery extends Query {
  @ApiParam(hidden = true)
  private Long projectId;
  @ApiParam(hidden = true)
  private Long userId;
  @ApiParam(hidden = true)
  private List<Long> projectIds;
  @ApiParam(hidden = true)
  private Integer managerFlg;
}

