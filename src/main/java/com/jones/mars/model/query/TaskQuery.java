package com.jones.mars.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value="消息查询参数")
@Builder
public class TaskQuery extends Query {
    @ApiModelProperty(value="任务接收人Id",name="userId")
    private Integer userId;
    @ApiModelProperty(value="项目Id",name="projectId")
    private Integer projectId;
    @ApiModelProperty(value="任务状态",name="status")
    private Integer status;
    @ApiModelProperty(value="任务类型",name="type")
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="开始日期",name="startDate")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="结束日期",name="endDate")
    private Date endDate;
}
