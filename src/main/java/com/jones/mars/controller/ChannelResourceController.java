package com.jones.mars.controller;

import com.jones.mars.config.LoginUser;
import com.jones.mars.model.ChannelResource;
import com.jones.mars.model.User;
import com.jones.mars.model.param.ChannelResourceAuthParam;
import com.jones.mars.model.param.ChannelResourceParam;
import com.jones.mars.model.query.ChannelResourceQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.ChannelResourceService;
import com.jones.mars.util.RandomString;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping({"/channelResource"})
@Slf4j
@Api(value = "渠道来源", tags = {"渠道来源"})
public class ChannelResourceController extends BaseController {
    @Autowired
    private ChannelResourceService service;

    @ApiOperation(value = "分页渠道来源列表", notes = "分页渠道来源列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam ChannelResourceQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "所有渠道来源列表", notes = "所有渠道来源列表")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam ChannelResourceQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "生成渠道token", notes = "生成渠道token")
    @GetMapping("{channelResourceId}/token")
    public BaseResponse getToken(@PathVariable String channelResourceId) {
        return service.generateToken(channelResourceId);
    }

    @ApiOperation(value = "生成渠道token", notes = "生成渠道token")
    @PostMapping("{channelResourceId}/validate")
    public BaseResponse validateAndLogin(@PathVariable String channelResourceId, @ApiParam(required=true) ChannelResourceAuthParam param) {
        param.setId(channelResourceId);
        return service.validateAndLogin(param);
    }

    @ApiOperation(value = "添加渠道来源", notes = "添加渠道来源")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) ChannelResourceParam param) {
//        param.setUserId(LoginUtil.getInstance().getUser().getId());
        return service.add(param);
    }

    @ApiOperation(value = "更新渠道来源", notes = "更新渠道来源")
    @PutMapping("{channelResourceId}")
    public BaseResponse update(
            @PathVariable String channelResourceId,
            @RequestBody @ApiParam(required=true) ChannelResourceParam param) {
//        param.setOperatorId(LoginUtil.getInstance().getUser().getId());
        param.setId(channelResourceId);
        return service.update(param);
    }

    @ApiOperation(value = "删除渠道来源", notes = "删除渠道来源")
    @DeleteMapping("/{channelResourceId}")
    public BaseResponse updateStatus(
            @PathVariable String channelResourceId) {
        return service.delete(channelResourceId);
    }

}
