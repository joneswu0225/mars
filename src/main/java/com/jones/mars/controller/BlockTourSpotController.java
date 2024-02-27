package com.jones.mars.controller;

import com.jones.mars.model.param.BlockTourSpotParam;
import com.jones.mars.model.param.BlockTourSpotSeqParam;
import com.jones.mars.model.query.BlockTourSpotQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockTourSpotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockTourSpot")
@Slf4j
@Api(value = "漫游热点管理", tags = {"漫游热点管理"})
public class BlockTourSpotController extends BaseController {

    @Autowired
    private BlockTourSpotService service;

    @ApiOperation(value = "获取模块下所有漫游热点", notes = "")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam BlockTourSpotQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "查看单个漫游热点", notes = "")
    @GetMapping("/{tourSpotId}")
    public BaseResponse findById(@PathVariable String tourSpotId) {
        return service.findById(tourSpotId);
    }

    @ApiOperation(value = "新增漫游热点", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockTourSpotParam param) {
        return service.insertContent(param);
    }

    @ApiOperation(value = "更新内容", notes = "")
    @PostMapping("/{tourSpotId}")
    public BaseResponse singleUpdate(
            @PathVariable String tourSpotId,
            @RequestBody @ApiParam(required=true) BlockTourSpotParam param) {
        param.setId(tourSpotId);
        return service.update(param);
    }

    @ApiOperation(value = "调整漫游热点顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) BlockTourSpotSeqParam param) {
        return service.updateBlockTourSpotSeq(param);
    }

    @ApiOperation(value = "删除漫游热点", notes = "")
    @DeleteMapping("{tourSpotId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String tourSpotId) {
        return service.delete(tourSpotId);
    }

}
