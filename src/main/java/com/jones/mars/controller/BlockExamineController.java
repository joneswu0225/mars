package com.jones.mars.controller;

import com.jones.mars.model.param.BlockExamineParam;
import com.jones.mars.model.query.BlockExamineQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockExamine")
@Slf4j
@Api(value = "船舶检查管理", tags = {"船舶检查管理"})
public class BlockExamineController extends BaseController {

    @Autowired
    private BlockExamineService service;

    @ApiOperation(value = "船舶检查列表", notes = "船舶检查列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam BlockExamineQuery query) {
        return service.findBlockExamines(query);
    }

    @ApiOperation(value = "新增船舶检查", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockExamineParam param) {
        param.setCreatorId(getLoginUser().getId());
        return service.add(param);
    }

    @ApiOperation(value = "获取模块下所有船舶检查", notes = "")
    @GetMapping("all")
    public BaseResponse allName(@ApiParam BlockExamineQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "获取模块下所有船舶检查名称", notes = "")
    @GetMapping("name")
    public BaseResponse allName(@RequestParam(name="blockId") @ApiParam(required=true) Integer blockId) {
        return service.allName(blockId);
    }

    @ApiOperation(value = "船舶检查详情", notes = "")
    @GetMapping("{blockExamineId}")
    public BaseResponse findOne(@PathVariable Integer blockExamineId) {
        return service.findBlockExamineById(blockExamineId);
    }


    @ApiOperation(value = "更新船舶检查", notes = "")
    @PutMapping("{blockExamineId}")
    public BaseResponse update(
            @PathVariable Integer blockExamineId,
            @RequestBody @ApiParam(required=true) BlockExamineParam param) {
        param.setOperatorId(getLoginUser().getId());
        param.setId(blockExamineId);
        return service.update(param);
    }

    @ApiOperation(value = "删除船舶检查", notes = "")
    @DeleteMapping("{blockExamineId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockExamineId) {
        return service.deleteExamine(blockExamineId);
    }

}
