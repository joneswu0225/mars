package com.jones.mars.controller;

import com.jones.mars.model.param.BlockContentParam;
import com.jones.mars.model.param.BlockContentSeqParam;
import com.jones.mars.model.query.BlockContentQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockContent")
@Slf4j
@Api(value = "模块内容管理", tags = {"模块内容管理"})
public class BlockContentController extends BaseController {

    @Autowired
    private BlockContentService service;

    @ApiOperation(value = "获取模块下所有模块内容", notes = "")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam BlockContentQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "查看单个模块内容", notes = "")
    @GetMapping("/{blockContentId}")
    public BaseResponse findById(@PathVariable String blockContentId) {
        return service.findById(blockContentId);
    }

    @ApiOperation(value = "新增模块内容", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockContentParam param) {
        return service.insertContent(param);
    }

    @ApiOperation(value = "更新内容", notes = "")
    @PostMapping("/{blockContentId}")
    public BaseResponse singleUpdate(
            @PathVariable String blockContentId,
            @RequestBody @ApiParam(required=true) BlockContentParam param) {
        param.setId(blockContentId);
        return service.update(param);
    }

    @ApiOperation(value = "调整模块内容顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) BlockContentSeqParam param) {
        return service.updateBlockContentSeq(param);
    }

    @ApiOperation(value = "删除模块内容", notes = "")
    @DeleteMapping("{blockContentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String blockContentId) {
        return service.delete(blockContentId);
    }

}
