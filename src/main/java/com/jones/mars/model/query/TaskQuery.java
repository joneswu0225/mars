package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="消息查询参数")
@Builder
public class TaskQuery extends Query {
    @ApiModelProperty(value="任务接收人Id",name="userId")
    private String userId;
    @ApiModelProperty(value="项目Id",name="projectId")
    private String projectId;
    @ApiModelProperty(value="任务状态",name="status")
    private Integer status;
    @ApiModelProperty(value="任务状态列表",name="statusIn")
    private Integer[] statusIn;
    @ApiModelProperty(value="任务类型",name="type")
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="开始日期",name="startDate")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="结束日期",name="endDate")
    private Date endDate;
    @ApiParam(hidden = true)
    private Date expireDateLt;
    @ApiParam(hidden = true)
    private Integer currentFlg;
}
