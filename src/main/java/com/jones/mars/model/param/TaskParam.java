package com.jones.mars.model.param;

import com.jones.mars.model.constant.TaskType;
import com.jones.mars.object.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.valueextraction.UnwrapByDefault;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskParam extends BaseObject {
  @ApiModelProperty(value="任务名称",name="name")
  private String name;
  @ApiModelProperty(value="任务详情",name="detail")
  private String detail;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @ApiModelProperty(value="开始时间",name="startDate")
  private Date startDate;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  @ApiModelProperty(value="到期时间",name="startDate")
  private Date expireDate;
  @NotNull(message = "任务类型不能为空")
  @ApiModelProperty(value="任务类型",name="type")
  private String type = TaskType.PROJECT_MODIFY.name();
  @ApiModelProperty(value="模块ID",name="blockId")
  private String blockId;
  @NotNull(message = "项目ID不能为空")
  @ApiModelProperty(value="项目ID",name="projectId")
  private String projectId;
  @ApiParam(hidden = true)
  @ApiModelProperty(value="项目协作人",name="projectId")
  private List<String> userIds;
  @ApiParam(hidden = true)
  @ApiModelProperty(value="项目版本",name="version")
  private Integer version;
  @ApiParam(hidden = true)
  private Integer currentFlg;
  @ApiParam(hidden = true)
  private Integer status;
  @ApiParam(hidden = true)
  private String userId;
  @ApiParam(hidden = true)
  private String createBy;
  @ApiParam(hidden = true)
  private String updateBy;
  @ApiParam(hidden = true)
  private String id;
}

