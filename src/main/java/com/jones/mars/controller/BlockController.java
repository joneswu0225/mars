package com.jones.mars.controller;

import com.jones.mars.model.Block;
import com.jones.mars.model.User;
import com.jones.mars.model.param.BlockParam;
import com.jones.mars.model.query.BlockQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockService;
import com.jones.mars.util.LoginUtil;
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

    @ApiOperation(value = "模块列表", notes = "模块列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam BlockQuery query) {
        return service.findBlocks(query);
    }

    @ApiOperation(value = "新增模块", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) BlockParam param) {
        param.setOperatorId(getLoginUser().getId());
        return service.add(Block.blockBuilder(param).build());
    }

    @ApiOperation(value = "获取公司下所有模块名称", notes = "")
    @GetMapping("name")
    public BaseResponse allName(@RequestParam(name="enterpriseId") @ApiParam(required=true) Integer enterpriseId) {
        //TODO 判断用户是否有该公司角色
        return service.allName(enterpriseId);
    }

    @ApiOperation(value = "模块详情", notes = "")
    @GetMapping("{blockId}")
    public BaseResponse findOne(@PathVariable Integer blockId) {
        return service.findById(blockId);
    }

    @ApiOperation(value = "更新模块", notes = "")
    @PutMapping("{blockId}")
    public BaseResponse update(
            @PathVariable Integer blockId,
            @RequestBody @ApiParam(required=true) BlockParam param) {
//        param.setOperatorId(getLoginUser().getId());
        param.setId(blockId);
        return service.update(param);
    }

    @ApiOperation(value = "删除模块", notes = "")
    @DeleteMapping("{blockId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer blockId) {
        return service.delete(blockId);
    }

}
