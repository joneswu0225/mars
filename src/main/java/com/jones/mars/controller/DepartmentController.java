package com.jones.mars.controller;

import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.param.DepartmentParam;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.query.DepartmentQuery;
import com.jones.mars.model.query.DepartmentUserQuery;
import com.jones.mars.model.query.EnterpriseUserQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.DepartmentService;
import com.jones.mars.service.DepartmentUserService;
import com.jones.mars.service.EnterpriseUserService;
import com.jones.mars.util.LoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/department")
@Slf4j
@Api(value = "部门管理", tags = {"部门管理"})
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService service;
    @Autowired
    private DepartmentUserService departmentUserService;
    @Autowired
    private EnterpriseUserService enterpriseUserService;

    @ApiOperation(value = "部门列表", notes = "部门列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam DepartmentQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "部门列表", notes = "部门列表")
    @GetMapping("all")
    public BaseResponse all(@ApiParam DepartmentQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "新增部门", notes = "新增部门")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) DepartmentParam param) {
//        param.setOperatorId(LoginUtil.getInstance().getUser().getId());
        return service.add(param);
    }

    @ApiOperation(value = "部门详情", notes = "部门详情")
    @GetMapping("{departmentId}")
    public BaseResponse findOne(@PathVariable Long departmentId) {
        return service.findById(departmentId);
    }

    @ApiOperation(value = "更新部门", notes = "更新部门")
    @PutMapping("{departmentId}")
    public BaseResponse update(
            @PathVariable Long departmentId,
            @RequestBody @ApiParam(required=true) DepartmentParam param) {
//        param.setOperatorId(LoginUtil.getInstance().getUser().getId());
        param.setDepartmentId(departmentId);
        return service.update(param);
    }

    @ApiOperation(value = "删除部门", notes = "后台调用")
    @DeleteMapping("{departmentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Long departmentId) {
        return service.delete(departmentId);
    }

    @ApiOperation(value = "部门人员列表", notes = "")
    @GetMapping("{departmentId}/user")
    public BaseResponse departmentUser(
            @PathVariable Long departmentId,
            @ApiParam(required=true) DepartmentUserQuery query) {
        query.setDepartmentId(departmentId);
        return departmentUserService.findByPage(query);
    }

    @ApiOperation(value = "部门所有人员列表", notes = "")
    @GetMapping("{departmentId}/user/all")
    public BaseResponse departmentUserAll(
            @PathVariable Long departmentId) {
        DepartmentUserQuery query = DepartmentUserQuery.builder().departmentId(departmentId).build();
        return departmentUserService.findAll(query);
    }

    @ApiOperation(value = "部门加人", notes = "")
    @PostMapping("{departmentId}/user")
    public BaseResponse addUser(
            @PathVariable Long departmentId,
            @RequestBody @ApiParam(required=true) DepartmentUserParam param) {
        param.setDepartmentId(departmentId);
        return service.addDepartmentUser(param);
    }

    @ApiOperation(value = "部门删人", notes = "")
    @DeleteMapping("{departmentId}/user/{userId}")
    public BaseResponse removeUser(@PathVariable @ApiParam(required=true) Long departmentId,
                                   @PathVariable @ApiParam(required=true) Long userId) {
        return service.removeDepartmentUser(DepartmentUserParam.builder().departmentId(departmentId).userId(userId).build());
    }

}
