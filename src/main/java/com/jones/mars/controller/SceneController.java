package com.jones.mars.controller;

import com.jones.mars.model.Scene;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.param.SceneParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.SceneService;
import com.jones.mars.service.SceneTypeService;
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
    @Autowired
    private SceneTypeService sceneTypeService;

    @ApiOperation(value = "场景列表", notes = "场景列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return sceneService.findByPage(query);
    }

    @ApiOperation(value = "新增场景", notes = "新增场景")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) SceneParam param) {
        return sceneService.add(param);
    }

    @ApiOperation(value = "场景详情", notes = "场景详情")
    @GetMapping("{sceneId}")
    public BaseResponse findOne(@PathVariable Integer sceneId) {
        return sceneService.findById(sceneId);
    }

    @ApiOperation(value = "更新场景", notes = "更新场景")
    @PutMapping("{sceneId}")
    public BaseResponse update(
            @PathVariable Integer sceneId,
            @Valid @RequestBody @ApiParam(required=true) SceneParam param) {
        return sceneService.update(Scene.sceneBuilder(param).id(sceneId).build());
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除场景", notes = "后台调用")
    @DeleteMapping("{sceneId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer sceneId) {
        return sceneService.delete(sceneId);
    }


}
