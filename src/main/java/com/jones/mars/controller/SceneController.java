package com.jones.mars.controller;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.Scene;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.model.param.SceneParam;
import com.jones.mars.model.param.SlicePanoParam;
import com.jones.mars.model.query.SceneQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.SceneService;
import com.jones.mars.util.KrpanoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @PostMapping(value="/slicePanoImage")
    public BaseResponse slicePanoImage(@RequestBody @ApiParam(required=true) SlicePanoParam param) {
        KrpanoUtil.PanoType panoType = param.getPanoType() == null ? KrpanoUtil.PanoType.FLAT : param.getPanoType();
        BaseResponse resp = BaseResponse.builder().code(ErrorCode.KRPANO_SLICE_PARAM_ERROR).build();
        if(param.getSceneId() != null){
            resp = sceneService.sliceImageBySceneId(param.getSceneId(), panoType);
        } else if (!StringUtils.isEmpty(param.getSceneCode())){
            resp = sceneService.sliceImageBySceneCode(param.getSceneCode(), panoType);
        } else if (param.getBlockId() != null){
            resp = sceneService.sliceImageByBlock(param.getBlockId(), panoType);
        }
        return resp;
    }

    @ApiOperation(value = "新增场景", notes = "新增场景,后台接口")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) SceneParam param) {
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
            @RequestBody @ApiParam(required=true) SceneParam param) {
        param.setId(sceneId);
        return sceneService.update(param);
    }

    @ApiOperation(value = "删除场景", notes = "后台调用,后台接口")
    @DeleteMapping("{sceneId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer sceneId) {
        return sceneService.delete(sceneId);
    }


    @ApiOperation(value = "公开场景", notes = "")
    @PatchMapping("{sceneId}/public")
    public BaseResponse publicScene(@PathVariable @ApiParam(required=true) Integer sceneId) {
        SceneParam param = SceneParam.builder().publicFlg(Scene.PUBLIC).build();
        param.setId(sceneId);
        return  sceneService.update(param);
    }

    @ApiOperation(value = "取消公开场景", notes = "")
    @PatchMapping("{sceneId}/unpublic")
    public BaseResponse unpublicScene(@PathVariable @ApiParam(required=true) Integer sceneId) {
        SceneParam param = SceneParam.builder().publicFlg(Scene.UNPUBLIC).build();
        param.setId(sceneId);
        return  sceneService.update(param);
    }

}
