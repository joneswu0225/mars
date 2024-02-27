package com.jones.mars.repository;

import com.jones.mars.model.Task;
import com.jones.mars.model.param.TaskParam;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper extends CommonMapper<Task> {
    List<Object> findAllName(Query query);
    Task findMaxVersionTask(Query query);
    Task findPrivateTask(Object query);
    String deleteCurrentTask(TaskParam param);
    void updateExpiredTaskStatus();
}
