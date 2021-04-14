package com.jones.mars.controller;

import com.jones.mars.model.WeprogramInfo;
import com.jones.mars.model.query.WeprogramInfoQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.WeprogramInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/WeprogramInfo")
@Slf4j
@Api(value = "小程序配置", tags = {"小程序配置"})
public class WeprogramInfoController extends BaseController {

    @Autowired
    private WeprogramInfoService service;

    @ApiOperation(value = "小程序配置分页列表", notes = "小程序配置分页列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam WeprogramInfoQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "小程序配置列表", notes = "小程序配置列表")
    @GetMapping("/all")
    public BaseResponse listAll(@ApiParam WeprogramInfoQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "新增小程序配置", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) WeprogramInfo param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新小程序配置", notes = "")
    @PutMapping("{weprogramInfoId}")
    public BaseResponse update(
            @PathVariable Integer weprogramInfoId,
            @RequestBody @ApiParam(required=true) WeprogramInfo param) {
        param.setId(weprogramInfoId);
        return service.update(param);
    }

    @ApiOperation(value = "删除小程序配置", notes = "")
    @DeleteMapping("{weprogramInfoId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer weprogramInfoId) {
        return service.delete(weprogramInfoId);
    }
}
