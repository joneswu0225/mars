package com.jones.mars.controller;

import com.jones.mars.model.Role;
import com.jones.mars.model.param.RoleParam;
import com.jones.mars.model.param.RoleUserParam;
import com.jones.mars.model.param.RolePermissionParam;
import com.jones.mars.model.param.UserRoleParam;
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
import java.util.Arrays;

@RestController
@RequestMapping("/role")
@Slf4j
@Api(value = "角色", tags = {"角色"})
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    @ApiOperation(value = "角色列表", notes = "角色列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam RoleQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "所有角色", notes = "所有角色")
    @GetMapping("all")
    public BaseResponse all(@ApiParam RoleQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) RoleUserParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PutMapping("{roleId}")
    public BaseResponse update(
            @PathVariable Integer roleId,
            @RequestBody @ApiParam(required=true) RoleParam param) {
        param.setId(roleId);
        return service.update(param);
    }

    @ApiOperation(value = "删除角色", notes = "后台调用")
    @DeleteMapping("{roleId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer roleId) {
        return service.delete(roleId);
    }

    @ApiOperation(value = "角色新增权限", notes = "")
    @PostMapping("{roleId}/permission")
    public BaseResponse addPermission(
            @PathVariable Integer roleId,
            @RequestBody @ApiParam(required=true) RolePermissionParam param) {
        param.setRoleId(roleId);
        return service.addPermission(param);
    }

    @ApiOperation(value = "角色移除权限", notes = "")
    @DeleteMapping("{roleId}/permission/{permissionId}")
    public BaseResponse removePermission(@PathVariable @ApiParam(required=true) Integer roleId,
                               @PathVariable @ApiParam(required=true) Integer permissionId) {
        return service.removePermission(permissionId);
    }

    @ApiOperation(value = "角色加人", notes = "")
    @PostMapping("{roleId}/user")
    public BaseResponse addUser(
            @PathVariable Integer roleId,
            @RequestBody @ApiParam(required=true) UserRoleParam param) {
        param.setRoleId(roleId);
        return service.addUser(param);
    }

    @ApiOperation(value = "角色删人", notes = "")
    @DeleteMapping("{roleId}/user/{userId}")
    public BaseResponse removeUser(@PathVariable @ApiParam(required=true) Integer roleId,
                               @PathVariable @ApiParam(required=true) Integer userId) {
        return service.removeUser(UserRoleParam.builder().roleId(roleId).userId(userId).build());
    }


}
