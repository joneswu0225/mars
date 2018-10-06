package com.jones.mars.controller;

import com.jones.mars.model.param.EnterpriseShownParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.EnterpriseShownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/enterpriseShown")
@Slf4j
@Api(value = "入驻品牌", tags = {"入驻品牌"})
public class EnterpriseShownController extends BaseController {

    @Autowired
    private EnterpriseShownService service;

    @ApiOperation(value = "入驻品牌列表", notes = "入驻品牌列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "新增入驻品牌", notes = "新增入驻品牌，后台接口")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) EnterpriseShownParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "更新入驻品牌", notes = "更新入驻品牌，后台接口")
    @PutMapping("{enterpriseShownId}")
    public BaseResponse update(
            @PathVariable Integer enterpriseShownId,
            @Valid @RequestBody @ApiParam(required=true) EnterpriseShownParam param) {
        param.setId(enterpriseShownId);
        return service.update(param);
    }

    @ApiOperation(value = "删除入驻品牌", notes = "后台接口")
    @DeleteMapping("{enterpriseShownId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer enterpriseShownId) {
        return service.delete(enterpriseShownId);
    }


}
