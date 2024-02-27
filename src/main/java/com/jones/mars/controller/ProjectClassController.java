package com.jones.mars.controller;

import com.jones.mars.model.BlockClass;
import com.jones.mars.model.param.BlockClassParam;
import com.jones.mars.model.param.ProjectClassParam;
import com.jones.mars.model.query.ProjectClassQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.ProjectClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projectClass")
@Slf4j
@Api(value = "二级分类", tags = {"二级分类"})
public class ProjectClassController extends BaseController {

    @Autowired
    private ProjectClassService service;

    @ApiOperation(value = "二级分类列表", notes = "二级分类列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam ProjectClassQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增二级分类", notes = "新增二级分类")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) ProjectClassParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新二级分类", notes = "更新二级分类")
    @PutMapping("{projectClassId}")
    public BaseResponse update(
            @PathVariable String projectClassId,
            @RequestBody @ApiParam(required=true) ProjectClassParam param) {
        BlockClass blockClass = BlockClass.builder().moduleId(param.getModuleId()).name(param.getName()).build();
        blockClass.setId(projectClassId);
        return service.update(blockClass);
    }


    @ApiOperation(value = "调整二级分类顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeBlockClassSeq(@RequestBody @ApiParam(required=true) BlockClassParam param) {
        return service.updateBlockClassSeq(param);
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除二级分类", notes = "后台调用")
    @DeleteMapping("{projectClassId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String projectClassId) {
        return service.delete(projectClassId);
    }


}
