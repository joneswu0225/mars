package com.jones.mars.controller;

import com.jones.mars.model.Message;
import com.jones.mars.model.query.MessageQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@Slf4j
@Api(value = "通知中心", tags = {"通知中心"})
public class MessageController extends BaseController {

    @Autowired
    private MessageService service;

    @ApiOperation(value = "通知列表", notes = "通知列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam MessageQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "更新通知已读状态", notes = "")
    @PatchMapping("{messageId}/read")
    public BaseResponse read(@PathVariable Integer messageId) {
        return service.updateStatus(messageId, Message.STATUS_READ);
    }

    @ApiOperation(value = "通知详情", notes = "")
    @GetMapping("{messageId}")
    public BaseResponse findOne(@PathVariable Integer messageId) {
        return service.findById(messageId);
    }

    @ApiOperation(value = "删除通知", notes = "")
    @DeleteMapping("{messageId}")
    public BaseResponse delete(@PathVariable Integer messageId) {
        return service.delete(messageId);
    }

    @ApiOperation(value = "未读消息条数", notes = "先传用户Id，登录拦截加上后就不用传")
    @GetMapping("unreadCount")
    public BaseResponse unreadCount() {
        //TODO 从session中获取用户ID
        return service.findUnreadCount(getLoginUser().getId());
    }

}
