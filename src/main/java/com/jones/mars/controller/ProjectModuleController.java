package com.jones.mars.controller;

import com.jones.mars.model.BlockModule;
import com.jones.mars.model.param.ProjectModuleParam;
import com.jones.mars.model.query.ProjectModuleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.ProjectModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projectModule")
@Slf4j
@Api(value = "一级分类", tags = {"一级分类"})
public class ProjectModuleController extends BaseController {

    @Autowired
    private ProjectModuleService service;

    @ApiOperation(value = "一级分类列表", notes = "一级分类列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam ProjectModuleQuery query) {
        return service.findAllProjectModule(query);
    }

    @ApiOperation(value = "新增一级分类", notes = "新增一级分类")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) ProjectModuleParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新一级分类", notes = "更新一级分类")
    @PutMapping("{projectModuleId}")
    public BaseResponse update(
            @PathVariable Integer projectModuleId,
            @RequestBody @ApiParam(required=true) ProjectModuleParam param) {
        BlockModule blockModule = BlockModule.builder().name(param.getName()).build();
        blockModule.setId(projectModuleId);
        return service.update(blockModule);
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除一级分类", notes = "后台调用")
    @DeleteMapping("{projectModuleId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer projectModuleId) {
        return service.delete(projectModuleId);
    }


}
