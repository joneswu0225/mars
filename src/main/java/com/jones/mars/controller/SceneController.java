package com.jones.mars.controller;

import com.jones.mars.model.param.SceneParam;
import com.jones.mars.model.query.SceneQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.SceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/scene")
@Slf4j
@Api(value = "场景", tags = {"场景"})
public class SceneController extends BaseController {

    @Autowired
    private SceneService sceneService;

    @ApiOperation(value = "场景列表", notes = "场景列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam SceneQuery query) {
        return sceneService.findByPage(query);
    }

    @ApiOperation(value = "所有场景列表", notes = "所有场景列表")
    @GetMapping("all")
    public BaseResponse all(@ApiParam SceneQuery query) {
        return sceneService.findAll(query);
    }

    @ApiOperation(value = "所有场景名称", notes = "用于场景选择下拉列表")
    @GetMapping("name")
    public BaseResponse allname(@ApiParam SceneQuery query) {
        return sceneService.allName(query);
    }

    @ApiOperation(value = "新增场景", notes = "新增场景,后台接口")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) SceneParam param) {
        return sceneService.add(param);
    }

    @ApiOperation(value = "场景详情", notes = "场景详情,后台接口")
    @GetMapping("{sceneId}")
    public BaseResponse findOne(@PathVariable Integer sceneId) {
        return sceneService.findById(sceneId);
    }

    @ApiOperation(value = "更新场景", notes = "更新场景,后台接口")
    @PutMapping("{sceneId}")
    public BaseResponse update(
            @PathVariable Integer sceneId,
            @Valid @RequestBody @ApiParam(required=true) SceneParam param) {
        param.setId(sceneId);
        return sceneService.update(param);
    }

    @ApiOperation(value = "删除场景", notes = "后台调用,后台接口")
    @DeleteMapping("{sceneId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer sceneId) {
        return sceneService.delete(sceneId);
    }


}
