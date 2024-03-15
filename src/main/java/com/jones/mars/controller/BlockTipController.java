package com.jones.mars.controller;

import com.jones.mars.model.param.BlockTipParam;
import com.jones.mars.model.param.BlockTourSpotParam;
import com.jones.mars.model.param.BlockTourSpotSeqParam;
import com.jones.mars.model.query.BlockTipQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockTipService;
import com.jones.mars.service.BlockTourSpotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockTip")
@Slf4j
@Api(value = "模块标签管理", tags = {"模块标签管理"})
public class BlockTipController extends BaseController {

    @Autowired
    private BlockTipService service;

    @ApiOperation(value = "获取模块下所有模块标签", notes = "")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam BlockTipQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "查看单个模块标签", notes = "")
    @GetMapping("/{blockTipd}")
    public BaseResponse findById(@PathVariable String blockTipId) {
        return service.findById(blockTipId);
    }

    @ApiOperation(value = "新增模块标签", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockTipParam param) {
        return service.insertContent(param);
    }

    @ApiOperation(value = "更新内容", notes = "")
    @PostMapping("/{blockTipId}")
    public BaseResponse singleUpdate(
            @PathVariable String blockTipId,
            @RequestBody @ApiParam(required=true) BlockTipParam param) {
        param.setId(blockTipId);
        return service.update(param);
    }

    @ApiOperation(value = "删除模块标签", notes = "")
    @DeleteMapping("{blockTipId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String blockTipId) {
        return service.delete(blockTipId);
    }

}
