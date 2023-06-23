package com.jones.mars.controller;

import com.jones.mars.model.param.BlockExamineContentParam;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import com.jones.mars.model.query.BlockExamineContentQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockExamineContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockExamineContent")
@Slf4j
@Api(value = "船舶检查内容管理", tags = {"船舶检查内容管理"})
public class BlockExamineContentController extends BaseController {

    @Autowired
    private BlockExamineContentService service;

    @ApiOperation(value = "获取船舶检查下所有船舶检查内容", notes = "")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam BlockExamineContentQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "查看单个船舶检查内容", notes = "")
    @GetMapping("/{blockExamineContentId}")
    public BaseResponse findById(@PathVariable Integer blockExamineContentId) {
        return service.findById(blockExamineContentId);
    }

    @ApiOperation(value = "新增船舶检查内容", notes = "")
    @PostMapping("/")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockExamineContentParam param) {
        return service.insertContent(param);
    }

    @ApiOperation(value = "更新内容", notes = "")
    @PostMapping("/{blockExamineContentId}")
    public BaseResponse singleUpdate(
            @PathVariable Integer blockExamineContentId,
            @RequestBody @ApiParam(required=true) BlockExamineContentParam param) {
        param.setId(blockExamineContentId);
        return service.update(param);
    }

    @ApiOperation(value = "调整船舶检查内容顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) BlockExamineContentSeqParam param) {
        return service.updateBlockExamineContentSeq(param);
    }

    @ApiOperation(value = "删除船舶检查内容", notes = "")
    @DeleteMapping("{blockExamineContentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockExamineContentId) {
        return service.delete(blockExamineContentId);
    }

}
