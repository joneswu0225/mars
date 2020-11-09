package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProjectUserParam {
  @ApiParam(hidden = true)
  private Integer userId;
  @ApiParam(hidden = true)
  private List<Integer> ids;
  @ApiParam(hidden = true)
  private Integer projectId;
  @ApiModelProperty(value="用户ID",name="userIds")
  private List<Integer> userIds = new ArrayList<>();
  @ApiParam(hidden = true)
  private Integer managerFlg = 0;
}

