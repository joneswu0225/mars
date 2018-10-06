package com.jones.mars.controller;

import com.jones.mars.model.BlockImage;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.param.BlockImageParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockImage")
@Slf4j
@Api(value = "模块平面图管理", tags = {"模块平面图管理"})
public class BlockImageController extends BaseController {

    @Autowired
    private BlockImageService service;

    @ApiOperation(value = "模块平面图列表", notes = "模块平面图列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增模块平面图", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockImageParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "获取模块下所有模块平面图名称", notes = "")
    @GetMapping("name")
    public BaseResponse allName(@RequestParam(name="blockId") @ApiParam(required=true) Integer blockId) {
        return service.allName(blockId);
    }

    @ApiOperation(value = "更新模块平面图", notes = "")
    @PutMapping("{blockImageId}")
    public BaseResponse update(
            @PathVariable Integer blockImageId,
            @RequestBody @ApiParam(required=true) BlockImageParam param) {
        param.setId(blockImageId);
        return service.update(param);
    }

    @ApiOperation(value = "删除模块平面图", notes = "")
    @DeleteMapping("{blockImageId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockImageId) {
        return service.delete(blockImageId);
    }

}
