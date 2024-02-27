package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserParam {
  @ApiParam(hidden = true)
  private String userId;
  @ApiParam(hidden = true)
  private List<String> ids;
  @ApiParam(hidden = true)
  private String projectId;
  @ApiModelProperty(value="用户ID",name="userIds")
  private List<String> userIds = new ArrayList<>();
  @ApiParam(hidden = true)
  private Integer managerFlg = 0;
}

