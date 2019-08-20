package com.jones.mars.model.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class RolePermissionParam {
  @ApiParam(hidden = true)
  private Integer roleId;
  @ApiParam(hidden = true)
  private Integer classId;
  @NotNull(message = "二级分类ID不能为空")
  @ApiModelProperty(value="二级分类ID",name="classIds")
  private List<Integer> classIds;
  @NotNull(message = "操作权限不能为空")
  @ApiModelProperty(value="操作权限，0：查看，1：创建",name="operation")
  private Integer operation;
}

