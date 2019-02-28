package com.jones.mars.controller;

import com.jones.mars.model.BlockSceneType;
import com.jones.mars.model.param.BlockSceneTypeParam;
import com.jones.mars.model.query.BlockSceneTypeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockSceneTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sceneType")
@Slf4j
@Api(value = "场景类型", tags = {"场景类型"})
public class BlockSceneTypeController extends BaseController {

    @Autowired
    private BlockSceneTypeService service;

    @ApiOperation(value = "场景类型列表", notes = "场景类型列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam BlockSceneTypeQuery query) {
        return service.findByPage(query);
    }


    @ApiOperation(value = "新增场景类型", notes = "新增场景类型")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockSceneTypeParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新场景类型", notes = "更新场景类型")
    @PutMapping("{blockSceneTypeId}")
    public BaseResponse update(
            @PathVariable Integer blockSceneTypeId,
            @RequestBody @ApiParam(required=true) BlockSceneTypeParam param) {
        BlockSceneType sceneType = BlockSceneType.builder().blockId(param.getBlockId()).name(param.getName()).detail(param.getDetail()).build();
        sceneType.setId(blockSceneTypeId);
        return service.update(sceneType);
    }

    @ApiOperation(value = "删除场景类型", notes = "后台调用")
    @DeleteMapping("{blockSceneTypeId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockSceneTypeId) {
        return service.delete(blockSceneTypeId);
    }


}
