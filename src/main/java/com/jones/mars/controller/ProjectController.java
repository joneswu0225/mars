package com.jones.mars.controller;

import com.jones.mars.model.Project;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.param.ProjectQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
@Slf4j
@Api(value = "项目管理", tags = {"项目管理"})
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService service;

    @ApiOperation(value = "项目列表", notes = "项目列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam ProjectQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增项目", notes = "新增项目")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) ProjectParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新项目", notes = "更新项目")
    @PutMapping("{projectId}")
    public BaseResponse update(
            @PathVariable Integer projectId,
            @Valid @RequestBody @ApiParam(required=true) ProjectParam param) {
        return service.update(Project.projectBuilder(param).id(projectId).build());
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除项目", notes = "")
    @DeleteMapping("{projectId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer projectId) {
        return service.delete(projectId);
    }


}
