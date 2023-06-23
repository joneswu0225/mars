package com.jones.mars.controller;

import com.jones.mars.model.param.BlockAttachmentParam;
import com.jones.mars.model.query.BlockAttachmentQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockAttachment")
@Slf4j
@Api(value = "设备管理", tags = {"设备管理"})
public class BlockAttachmentController extends BaseController {

    @Autowired
    private BlockAttachmentService service;

    @ApiOperation(value = "设备列表", notes = "设备列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam BlockAttachmentQuery query) {
        return service.findBlockAttachments(query);
    }

    @ApiOperation(value = "新增设备", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockAttachmentParam param) {
        param.setCreatorId(getLoginUser().getId());
        return service.add(param);
    }

    @ApiOperation(value = "获取模块下所有设备", notes = "")
    @GetMapping("all")
    public BaseResponse all(@ApiParam BlockAttachmentQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "获取模块下所有设备名称", notes = "")
    @GetMapping("name")
    public BaseResponse allName(@RequestParam(name="blockId") @ApiParam(required=true) Integer blockId) {
        return service.allName(blockId);
    }

    @ApiOperation(value = "设备详情", notes = "")
    @GetMapping("{blockAttachmentId}")
    public BaseResponse findOne(@PathVariable Integer blockAttachmentId) {
        return service.findBlockAttachmentById(blockAttachmentId);
    }


    @ApiOperation(value = "更新设备", notes = "")
    @PutMapping("{blockAttachmentId}")
    public BaseResponse update(
            @PathVariable Integer blockAttachmentId,
            @RequestBody @ApiParam(required=true) BlockAttachmentParam param) {
        param.setOperatorId(getLoginUser().getId());
        param.setId(blockAttachmentId);
        return service.update(param);
    }

    @ApiOperation(value = "删除设备", notes = "")
    @DeleteMapping("{blockAttachmentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockAttachmentId) {
        return service.deleteAttachment(blockAttachmentId);
    }

}
