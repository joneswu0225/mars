package com.jones.mars.controller;

import com.jones.mars.model.User;
import com.jones.mars.model.param.UserLoginParam;
import com.jones.mars.model.param.UserPasswordRestParam;
import com.jones.mars.model.param.UserRegistParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.UserService;
import com.jones.mars.support.ValidMobile;
import com.jones.mars.util.LoginUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "注册/登录相关", tags = {"注册/登录相关"})
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("regist")
    public BaseResponse regist(@Valid @RequestBody @ApiParam(required=true) UserRegistParam param) {
        return userService.add(User.builder().mobile(param.getMobile()).password(param.getPassword()).userType(User.COMMON).build());
    }

    @ApiOperation(value = "手机号查重", notes = "手机号查重")
    @GetMapping("{mobile}/exists")
    public BaseResponse exists(@PathVariable (value="mobile") @ValidMobile @ApiParam String mobile) {
        return userService.mobileExists(mobile);
    }

    @ApiOperation(value = "获取验证码", notes = "注册时获取验证码手机号可以为空，其他情况需要有手机号")
    @GetMapping("/verifyCode")
    public BaseResponse getVerifyCode(@RequestParam (value="mobile") @ValidMobile @ApiParam String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return BaseResponse.builder().data("HSE3").build();
        } else {
            return userService.getVerifyCode(mobile);
        }
    }

    @ApiOperation(value = "重置密码", notes = "重置密码")
    @PostMapping("password/reset")
    public BaseResponse passwordReset(@Valid @RequestBody @ApiParam(required=true) UserPasswordRestParam param) {
        return userService.resetPassword(User.builder().mobile(param.getMobile()).password(param.getPassword()).verifyCode(param.getVerifyCode()).build());
    }

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("login")
    public BaseResponse login(@RequestBody @ApiParam(required=true) UserLoginParam param) {
        return userService.doLogin(param);
    }

    @ApiOperation(value = "注销", notes = "注销")
    @PostMapping("logout")
    public BaseResponse logout() {
        LoginUtil.getInstance().removeUser();
        return BaseResponse.builder().build();
    }

}
