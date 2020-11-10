package com.jones.mars.controller;

import com.jones.mars.model.param.HotspotContentParam;
import com.jones.mars.model.param.HotspotContentParams;
import com.jones.mars.model.param.HotspotContentSeqParam;
import com.jones.mars.model.param.HotspotParam;
import com.jones.mars.model.query.HotspotContentQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.HotspotContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/hotspotContent")
@Slf4j
@Api(value = "热点内容管理", tags = {"热点内容管理"})
public class HotspotContentController extends BaseController {

    @Autowired
    private HotspotContentService service;

    @ApiOperation(value = "获取热点下所有热点内容", notes = "")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam HotspotContentQuery query) {
        return service.findAll(query);
    }

    @ApiOperation(value = "查看单个热点内容", notes = "")
    @GetMapping("/{hotspotContentId}")
    public BaseResponse findById(@PathVariable Integer hotspotContentId) {
        return service.findById(hotspotContentId);
    }

    @ApiOperation(value = "批量新增/更新热点内容", notes = "")
    @PostMapping("")
    public BaseResponse save(@RequestBody @ApiParam(required=true) HotspotContentParams param) {
        return service.save(param);
    }

    @ApiOperation(value = "新增热点内容", notes = "")
    @PostMapping("/single")
    public BaseResponse singleAdd(@RequestBody @ApiParam(required=true) HotspotContentParam param) {
        return service.insertContent(param);
    }

    @ApiOperation(value = "更新内容", notes = "")
    @PostMapping("/{hotspotContentId}")
    public BaseResponse singleUpdate(
            @PathVariable Integer hotspotContentId,
            @RequestBody @ApiParam(required=true) HotspotContentParam param) {
        param.setId(hotspotContentId);
        return service.updateContents(Arrays.asList(param));
    }

    @ApiOperation(value = "调整热点内容顺序", notes = "")
    @PostMapping("/changeSeq")
    public BaseResponse changeSeq(@RequestBody @ApiParam(required=true) HotspotContentSeqParam param) {
        return service.updateHotspotContentSeq(param);
    }

    @ApiOperation(value = "删除热点内容", notes = "")
    @DeleteMapping("{hotspotContentId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer hotspotContentId) {
        return service.delete(hotspotContentId);
    }

}
