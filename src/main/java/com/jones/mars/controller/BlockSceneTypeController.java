package com.jones.mars.controller;

import com.jones.mars.model.BlockSceneType;
import com.jones.mars.model.param.BlockExamineContentSeqParam;
import com.jones.mars.model.param.BlockSceneTypeParam;
import com.jones.mars.model.param.BlockSceneTypeSeqParam;
import com.jones.mars.model.query.BlockSceneTypeQuery;
import com.jones.mars.model.query.HaiteBlockSceneTypeQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockSceneTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return service.insertSceneType(param);
    }

    @ApiOperation(value = "海特场景类型", notes = "海特场景类型")
    @PostMapping("/haite/all")
    public BaseResponse allName(@RequestBody @ApiParam(required=true) HaiteBlockSceneTypeQuery query) {
        List<BlockSceneType> sceneTypeList = (List<BlockSceneType>) service.allName(query.getBlock_id()).getData();
        List<Integer> sceneTypeIdList = sceneTypeList.stream().map(s->s.getId()).collect(Collectors.toList());
        Map<String, Object> result = new HashMap<>();
        result.put("block_id", query.getBlock_id());
        result.put("scene_type_ids", sceneTypeIdList);
        return BaseResponse.builder().data(result).build();
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

    @ApiOperation(value = "调整场景类型顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) BlockSceneTypeSeqParam param) {
        return service.updateBlockSceneTypeSeq(param);
    }
    @ApiOperation(value = "删除场景类型", notes = "后台调用")
    @DeleteMapping("{blockSceneTypeId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockSceneTypeId) {
        return service.delete(blockSceneTypeId);
    }


}
