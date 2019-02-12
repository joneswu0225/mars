package com.jones.mars.controller;

import com.jones.mars.model.User;
import com.jones.mars.model.param.UserParam;
import com.jones.mars.model.param.UserProfileParam;
import com.jones.mars.model.query.UserQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户管理", tags = {"用户管理"})
public class UserController extends BaseController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "用户列表", notes = "用户列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam UserQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增用户", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) UserParam param) {
        return service.add(User.builder().mobile(param.getMobile()).userType(param.getUserType()).build());
    }

    // 管理员
    @ApiOperation(value = "更新用户", notes = "")
    @PutMapping("{userId}")
    public BaseResponse update(
            @PathVariable Integer userId,
            @RequestBody @ApiParam(required=true) UserParam param) {
        User user = User.builder().userType(param.getUserType()).status(param.getStatus()).build();
        user.setId(userId);
        return service.update(user);
    }

    @ApiOperation(value = "更新用户信息", notes = "")
    @PutMapping("{userId}/profile")
    public BaseResponse update(
            @PathVariable Integer userId,
            @RequestBody @ApiParam(required=true) UserProfileParam param) {
        param.setUserId(userId);
        return service.updateProfile(param);
    }

    @ApiOperation(value = "删除用户", notes = "")
    @DeleteMapping("{userId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer userId) {
        return service.delete(userId);
    }

}
