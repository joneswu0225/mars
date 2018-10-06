package com.jones.mars.controller;

import com.jones.mars.model.param.DepartmentParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/department")
@Slf4j
@Api(value = "部门管理", tags = {"部门管理"})
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService service;

    @ApiOperation(value = "部门列表", notes = "部门列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增部门", notes = "新增部门")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) DepartmentParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "部门详情", notes = "部门详情")
    @GetMapping("{departmentId}")
    public BaseResponse findOne(@PathVariable Integer departmentId) {
        return service.findById(departmentId);
    }

    @ApiOperation(value = "更新部门", notes = "更新部门")
    @PutMapping("{departmentId}")
    public BaseResponse update(
            @PathVariable Integer departmentId,
            @Valid @RequestBody @ApiParam(required=true) DepartmentParam param) {
        param.setId(departmentId);
        return service.update(param);
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除部门", notes = "后台调用")
    @DeleteMapping("{departmentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer departmentId) {
        return service.delete(departmentId);
    }


}
