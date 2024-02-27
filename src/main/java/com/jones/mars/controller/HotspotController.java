package com.jones.mars.controller;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.param.HotspotParam;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.HotspotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotspot")
@Slf4j
@Api(value = "热点管理", tags = {"热点管理"})
public class HotspotController extends BaseController {

    @Autowired
    private HotspotService service;

    @ApiOperation(value = "热点列表", notes = "热点列表，后台接口")
    @GetMapping("")
    public BaseResponse list(@ApiParam HotspotQuery query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "获取所有热点", notes = "后台接口")
    @GetMapping("/all")
    public BaseResponse all(@ApiParam HotspotQuery query) {
        return service.findAllByQuery(query);
    }

    @ApiOperation(value = "新增热点", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) HotspotParam param) {
        if(StringUtils.isEmpty(param.getCode())){
            param.setCode("hs_" + System.currentTimeMillis());
        }
        if(StringUtils.isEmpty(param.getType())){
            param.setType(Hotspot.TYPE_DEFAULT);
        }
        return service.add(param);
    }

    @ApiOperation(value = "更新热点", notes = "")
    @PutMapping("{hotspotId}")
    public BaseResponse update(
            @PathVariable String hotspotId,
            @RequestBody @ApiParam(required=true) HotspotParam param) {
        param.setId(hotspotId);
        return service.update(param);
    }

    @ApiOperation(value = "删除热点", notes = "")
    @DeleteMapping("{hotspotId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) String hotspotId) {
        return service.delete(hotspotId);
    }

}
