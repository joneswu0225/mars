package com.jones.mars.model.param;

import com.jones.mars.model.constant.TaskType;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.valueextraction.UnwrapByDefault;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class TaskParam extends BaseObject {
  @ApiModelProperty(value="任务名称",name="name")
  private String name;
  @ApiModelProperty(value="任务详情",name="detail")
  private String detail;
  @ApiModelProperty(value="开始时间",name="startDate")
  private Date startDate;
  @ApiModelProperty(value="到期时间",name="startDate")
  private Date expireDate;
  @NotNull(message = "任务类型不能为空")
  @ApiModelProperty(value="任务类型",name="type")
  private String type = TaskType.PROJECT_MODIFY.name();
  @ApiModelProperty(value="模块ID",name="blockId")
  private Integer blockId;
  @ApiModelProperty(value="项目ID",name="projectId")
  private Integer projectId;
  @ApiParam(hidden = true)
  @ApiModelProperty(value="项目协作人",name="projectId")
  private List<Integer> userIds;
  @ApiParam(hidden = true)
  @ApiModelProperty(value="项目版本",name="version")
  private Integer version;
  @ApiParam(hidden = true)
  private Integer currentFlg;
  @ApiParam(hidden = true)
  private Integer status;
  @ApiParam(hidden = true)
  private Integer userId;
  @ApiParam(hidden = true)
  private Integer createBy;
  @ApiParam(hidden = true)
  private Integer updateBy;
}

