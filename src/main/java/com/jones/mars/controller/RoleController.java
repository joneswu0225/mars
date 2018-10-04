package com.jones.mars.controller;

import com.jones.mars.model.Role;
import com.jones.mars.model.param.RoleParam;
import com.jones.mars.model.query.RoleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
@Slf4j
@Api(value = "权限", tags = {"权限"})
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    @ApiOperation(value = "权限列表", notes = "权限列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam RoleQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增权限", notes = "新增权限")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) RoleParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新权限", notes = "更新权限")
    @PutMapping("{roleId}")
    public BaseResponse update(
            @PathVariable Integer roleId,
            @Valid @RequestBody @ApiParam(required=true) RoleParam param) {
        return service.update(Role.builder().id(roleId).name(param.getName()).build());
    }

    @ApiOperation(value = "删除权限", notes = "后台调用")
    @DeleteMapping("{roleId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer roleId) {
        return service.delete(roleId);
    }


}
