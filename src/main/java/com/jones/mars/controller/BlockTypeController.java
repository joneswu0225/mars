package com.jones.mars.controller;

import com.jones.mars.model.BlockType;
import com.jones.mars.model.param.BlockTypeParam;
import com.jones.mars.model.param.BlockTypeSeqParam;
import com.jones.mars.model.query.BlockTypeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockType")
@Slf4j
@Api(value = "模块类型", tags = {"模块类型"})
public class BlockTypeController extends BaseController {

    @Autowired
    private BlockTypeService service;

    @ApiOperation(value = "模块类型列表", notes = "模块类型列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam BlockTypeQuery query) {
        return service.findByPage(query);
    }


    @ApiOperation(value = "所有模块类型列表", notes = "所有模块类型列表")
    @GetMapping("all")
    public BaseResponse all(@ApiParam BlockTypeQuery query) {
        return service.findAll(query);
    }


    @ApiOperation(value = "新增模块类型", notes = "新增模块类型")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockTypeParam param) {
        return service.insertBlockType(param);
    }

    @ApiOperation(value = "更新模块类型", notes = "更新模块类型")
    @PutMapping("{blockTypeId}")
    public BaseResponse update(
            @PathVariable Long blockTypeId,
            @RequestBody @ApiParam(required=true) BlockTypeParam param) {
        BlockType sceneType = BlockType.builder().enterpriseId(param.getEnterpriseId()).name(param.getName()).detail(param.getDetail()).build();
        sceneType.setId(blockTypeId);
        return service.update(sceneType);
    }

    @ApiOperation(value = "调整模块类型顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) BlockTypeSeqParam param) {
        return service.updateBlockTypeSeq(param);
    }
    @ApiOperation(value = "删除模块类型", notes = "后台调用")
    @DeleteMapping("{blockTypeId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Long blockTypeId) {
        return service.delete(blockTypeId);
    }


}
