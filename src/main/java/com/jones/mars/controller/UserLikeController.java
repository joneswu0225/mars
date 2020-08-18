package com.jones.mars.controller;

import com.jones.mars.model.param.UserLikeParam;
import com.jones.mars.model.query.UserLikeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.UserLikeService;
import com.jones.mars.util.LoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userLike")
@Slf4j
@Api(value = "点赞管理", tags = {"点赞管理"})
public class UserLikeController extends BaseController {

    @Autowired
    private UserLikeService service;

    @ApiOperation(value = "点赞结果", notes = "点赞结果")
    @GetMapping("result")
    public BaseResponse list(@ApiParam UserLikeQuery query) {
        return service.findLikeResult(query);
    }

    @ApiOperation(value = "当前用户点赞结果", notes = "当前用户点赞结果")
    @GetMapping("privateResult")
    public BaseResponse privateResult(@ApiParam UserLikeQuery query) {
        query.setUserId(LoginUtil.getInstance().getUser().getId());
        return service.findLikeResult(query);
    }

    @ApiOperation(value = "新增点赞", notes = "新增点赞")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) UserLikeParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "取消点赞", notes = "取消点赞")
    @DeleteMapping("cancel")
    public BaseResponse findOne(@RequestBody @ApiParam(required=true) UserLikeParam param) {
        return service.cancel(param);
    }

}
