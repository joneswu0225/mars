package com.jones.mars.controller;

import com.jones.mars.model.param.TaskParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.query.TaskQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.TaskService;
import com.jones.mars.util.LoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@Slf4j
@Api(value = "任务管理", tags = {"任务管理"})
public class TaskController extends BaseController {

    @Autowired
    private TaskService service;

    @ApiOperation(value = "任务列表", notes = "任务列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam TaskQuery query) {
        return service.findTaskList(query);
    }

    @ApiOperation(value = "新增任务", notes = "新增任务")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) TaskParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "任务详情", notes = "任务详情")
    @GetMapping("{taskId}")
    public BaseResponse findOne(@PathVariable Integer taskId) {
        return service.findById(taskId);
    }

    @ApiOperation(value = "更新任务状态", notes = "更新任务状态")
    @PutMapping("{taskId}")
    public BaseResponse update(
            @PathVariable Integer taskId,
            @RequestBody @ApiParam(required=true) TaskParam param) {
        param.setUpdateBy(LoginUtil.getInstance().getUser().getId());
        param.setId(taskId);
        return service.update(param);
    }

    @ApiOperation(value = "所有任务", notes = "所有任务")
    @GetMapping("all")
    public BaseResponse all(@ApiParam TaskQuery query) {
        return service.findAllTasks(query);
    }

    @ApiOperation(value = "任务日历", notes = "任务日历")
    @GetMapping("calendar")
    public BaseResponse findCalendar(@ApiParam TaskQuery query) {
        return service.getCalendar(query);
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除任务", notes = "后台调用")
    @DeleteMapping("{taskId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer taskId) {
        return service.delete(taskId);
    }

}
