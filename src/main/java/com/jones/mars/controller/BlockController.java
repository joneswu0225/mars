package com.jones.mars.controller;

import com.jones.mars.model.param.BlockParam;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockSceneTypeService;
import com.jones.mars.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/block")
@Slf4j
@Api(value = "模块管理", tags = {"模块管理"})
public class BlockController extends BaseController {

    @Autowired
    private BlockService service;
    @Autowired
    private BlockSceneTypeService blockSceneTypeService;

    @ApiOperation(value = "模块列表", notes = "模块列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam BlockQuery query) {
        return service.findBlocks(query);
    }

    @ApiOperation(value = "模块列表", notes = "授权的模块列表")
    @GetMapping("authBlocks")
    public BaseResponse authBlocks(@ApiParam BlockQuery query) {
        return service.findAuthBlocks(query);
    }

    @ApiOperation(value = "新增模块", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockParam param) {
//        param.setOperatorId(getLoginUser().getId());
        return service.add(param);
    }

    @ApiOperation(value = "获取公司下所有模块名称", notes = "")
    @GetMapping("name")
    public BaseResponse allName(@RequestParam(name="enterpriseId") @ApiParam(required=true) String enterpriseId) {
        return service.allName(enterpriseId);
    }

    @ApiOperation(value = "企业共建人列表", notes = "")
    @GetMapping("{blockId}/class/{classId}/user") 
    public BaseResponse findBlockUser(@PathVariable String blockId,@PathVariable String classId) {
        return service.findClassPartner(classId);
    }

    @ApiOperation(value = "模块详情", notes = "")
    @GetMapping("{blockId}")
    public BaseResponse findOne(@PathVariable String blockId) {
        return service.findBlockById(blockId);
    }

    @ApiOperation(value = "跟据code查询模块详情", notes = "")
    @GetMapping("/code/{code}")
    public BaseResponse findOneByCode(@PathVariable String code) {
        return service.findOneByCode(code);
    }

    @ApiOperation(value = "更新模块", notes = "")
    @PutMapping("{blockId}")
    public BaseResponse update(
            @PathVariable String blockId,
            @RequestBody @ApiParam(required=true) BlockParam param) {
//        param.setOperatorId(getLoginUser().getId());
        param.setId(blockId);
        return service.update(param);
    }

    @ApiOperation(value = "删除模块", notes = "")
    @DeleteMapping("{blockId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String blockId) {
        return service.delete(blockId);
    }

    @ApiOperation(value = "模块下所有场景类型列表", notes = "模块下所有场景类型列表")
    @GetMapping("{blockId}/sceneType")
    public BaseResponse sceneType(@PathVariable @ApiParam(required=true) String blockId) {
        return blockSceneTypeService.findSceneTypeProjectScene(blockId);
    }

}
