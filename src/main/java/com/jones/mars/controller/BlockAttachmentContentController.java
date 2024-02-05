package com.jones.mars.controller;

import com.jones.mars.model.param.BlockAttachmentContentParam;
import com.jones.mars.model.param.BlockAttachmentContentSeqParam;
import com.jones.mars.model.query.BlockAttachmentContentQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockAttachmentContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockAttachmentContent")
@Slf4j
@Api(value = "设备内容管理", tags = {"设备内容管理"})
public class BlockAttachmentContentController extends BaseController {

    @Autowired
    private BlockAttachmentContentService service;

    @ApiOperation(value = "获取设备下所有设备内容", notes = "")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam BlockAttachmentContentQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "查看单个设备内容", notes = "")
    @GetMapping("/{blockAttachmentContentId}")
    public BaseResponse findById(@PathVariable Long blockAttachmentContentId) {
        return service.findById(blockAttachmentContentId);
    }

    @ApiOperation(value = "新增设备内容", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockAttachmentContentParam param) {
        return service.insertContent(param);
    }

    @ApiOperation(value = "更新内容", notes = "")
    @PostMapping("/{blockAttachmentContentId}")
    public BaseResponse singleUpdate(
            @PathVariable Long blockAttachmentContentId,
            @RequestBody @ApiParam(required=true) BlockAttachmentContentParam param) {
        param.setId(blockAttachmentContentId);
        return service.update(param);
    }

    @ApiOperation(value = "调整设备内容顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) BlockAttachmentContentSeqParam param) {
        return service.updateBlockAttachmentContentSeq(param);
    }

    @ApiOperation(value = "删除设备内容", notes = "")
    @DeleteMapping("{blockAttachmentContentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Long blockAttachmentContentId) {
        return service.delete(blockAttachmentContentId);
    }

}
