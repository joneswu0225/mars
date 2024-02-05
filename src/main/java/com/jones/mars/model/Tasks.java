package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.Message;
import com.jones.mars.model.Task;
import com.jones.mars.model.param.TaskParam;
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
@TableName("task")
public class Tasks {
    private List<Task> taskList = new ArrayList<>();

    public Tasks(TaskParam param){
        for(Long userId : param.getUserIds()){
            taskList.add(Task.builder()
                    .name(param.getName())
                    .detail(param.getDetail())
                    .type(param.getType())
                    .status(param.getStatus())
                    .startDate(param.getStartDate())
                    .expireDate(param.getExpireDate())
                    .blockId(param.getBlockId())
                    .projectId(param.getProjectId())
                    .userId(userId)
                    .version(param.getVersion())
                    .currentFlg(param.getCurrentFlg())
                    .createBy(param.getCreateBy())
                    .updateBy(param.getUpdateBy())
                    .build());
        }
    }
}

