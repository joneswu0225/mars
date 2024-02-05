package com.jones.mars.controller;

import com.jones.mars.config.LoginUser;
import com.jones.mars.model.User;
import com.jones.mars.model.param.CommentParam;
import com.jones.mars.model.query.CommentQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/comment"})
@Slf4j
@Api(value = "评论", tags = {"评论"})
public class CommentController extends BaseController {
    @Autowired
    private CommentService service;

    @ApiOperation(value = "分页评论列表", notes = "分页评论列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam CommentQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "所有评论列表", notes = "所有评论列表")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam CommentQuery query) {
        return service.findAll(query);
    }

    @LoginUser(role = {User.COMMON, User.ENTMANAGER, User.ENTMANAGER})
    @ApiOperation(value = "添加评论", notes = "添加评论")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) CommentParam param) {
//        param.setUserId(LoginUtil.getInstance().getUser().getId());
        return service.add(param);
    }

    @ApiOperation(value = "更新评论", notes = "更新评论")
    @PutMapping("{commentId}")
    public BaseResponse update(
            @PathVariable Long commentId,
            @RequestBody @ApiParam(required=true) CommentParam param) {
//        param.setOperatorId(LoginUtil.getInstance().getUser().getId());
        param.setId(commentId);
        return service.update(param);
    }

    @ApiOperation(value = "删除评论", notes = "删除评论")
    @DeleteMapping("/{commentId}")
    public BaseResponse updateStatus(
            @PathVariable Long commentId) {
        return service.delete(commentId);
    }

}
