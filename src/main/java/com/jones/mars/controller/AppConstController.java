package com.jones.mars.controller;

import com.jones.mars.model.AppConst;
import com.jones.mars.model.param.AppConstParam;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.query.AppConstQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.AppConstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appConst")
@Slf4j
@Api(value = "系統常量", tags = {"系統常量"})
public class AppConstController extends BaseController {

    @Autowired
    private AppConstService service;

    @ApiOperation(value = "系統常量分页列表", notes = "系統常量分页列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam AppConstQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "系統常量列表", notes = "系統常量列表")
    @GetMapping("/all")
    public BaseResponse listAll(@ApiParam AppConstQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "首页推荐", notes = "首页推荐")
    @GetMapping("/recommendProject")
    public BaseResponse listRecommendProject() {
        return service.findRecommendProject();
    }

    @ApiOperation(value = "系统默认参数列", notes = "系统默认参数")
    @GetMapping("/defaultInfo")
    public BaseResponse appDefaultInfo() {
        return service.findAll(AppConstQuery.builder().nameLike(AppConst.APP_DEFAULT_PREFIX).build());
    }

    @ApiOperation(value = "新增系統常量", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) AppConst param) {
        return service.add(param);
    }

    @ApiOperation(value = "系統常量顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSceneSeq(@RequestBody @ApiParam(required=true) AppConstParam param) {
        return service.updateAppConstSeq(param);
    }

    @ApiOperation(value = "更新系統常量", notes = "")
    @PutMapping("{appConstId}")
    public BaseResponse update(
            @PathVariable Long appConstId,
            @RequestBody @ApiParam(required=true) AppConst param) {
        param.setId(appConstId);
        return service.update(param);
    }

    @ApiOperation(value = "删除系統常量", notes = "")
    @DeleteMapping("{appConstId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Long appConstId) {
        return service.delete(appConstId);
    }
}
