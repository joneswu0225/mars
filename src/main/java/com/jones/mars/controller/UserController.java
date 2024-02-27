package com.jones.mars.controller;

import com.aliyuncs.utils.StringUtils;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.User;
import com.jones.mars.model.param.UserParam;
import com.jones.mars.model.param.UserProfileParam;
import com.jones.mars.model.query.UserQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.UserService;
import com.jones.mars.util.LoginUtil;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public BaseResponse add(@Validated @RequestBody @ApiParam(required=true) UserParam param) {
        User loginUser = LoginUtil.getInstance().getUser();
        if(loginUser.getUserType() == User.COMMON){
            return BaseResponse.builder().code(ErrorCode.ADMIN_ONLY).build();
        }
        return service.add(User.builder().mobile(param.getMobile()).password(param.getPassword()).userType(param.getUserType()).build());
    }

    // 管理员
    @ApiOperation(value = "验证用户状态", notes = "管理员对用户状态修改")
    @PutMapping("{userId}")
    public BaseResponse update(
            @PathVariable String userId,
            @RequestBody @ApiParam(required=true) UserParam param) {
        User loginUser = LoginUtil.getInstance().getUser();
        if(loginUser.getUserType() == User.COMMON){
            return BaseResponse.builder().code(ErrorCode.ADMIN_ONLY).build();
        }
        User user = User.builder().userType(param.getUserType()).status(param.getStatus()).build();
        if(!StringUtils.isEmpty(param.getPassword())){
            user.setPassword(param.getPassword());
        }
        user.setId(userId);
        return service.update(user);
    }

    @ApiOperation(value = "查看个人信息", notes = "")
    @GetMapping("personal")
    public BaseResponse personal() {
        return service.personal(getLoginUser().getId());
    }

    @ApiOperation(value = "查看用户信息", notes = "")
    @GetMapping("{userId}")
    public BaseResponse userInfo(@PathVariable String userId) {
        return service.personal(userId);
    }

    @ApiOperation(value = "更新用户信息", notes = "")
    @PutMapping("{userId}/profile")
    public BaseResponse update(
            @PathVariable String userId,
            @RequestBody @ApiParam(required=true) UserProfileParam param) {
        param.setUserId(userId);
        return service.updateProfile(param);
    }

    @ApiOperation(value = "删除用户", notes = "")
    @DeleteMapping("{userId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String userId) {
        return service.delete(userId);
    }

}
