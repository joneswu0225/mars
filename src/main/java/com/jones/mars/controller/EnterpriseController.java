package com.jones.mars.controller;

import com.jones.mars.model.Enterprise;
import com.jones.mars.model.param.EnterpriseParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockService;
import com.jones.mars.service.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise")
@Slf4j
@Api(value = "企业管理", tags = {"企业管理"})
public class EnterpriseController extends BaseController {

    @Autowired
    private BlockService blockService;
    @Autowired
    private EnterpriseService service;

    @ApiOperation(value = "企业列表", notes = "企业列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return service.findByPage(query);
    }


    @ApiOperation(value = "新增企业", notes = "")
    @PostMapping("")
    public BaseResponse add(@RequestBody @ApiParam(required=true) EnterpriseParam param) {
        return service.add(param);
    }

    @ApiOperation(value = "企业详情", notes = "企业详情")
    @GetMapping("{enterpriseId}")
    public BaseResponse findOne(@PathVariable Integer enterpriseId) {
        return service.findById(enterpriseId);
    }

    @ApiOperation(value = "更新企业", notes = "")
    @PutMapping("{enterpriseId}")
    public BaseResponse update(
            @PathVariable Integer enterpriseId,
            @RequestBody @ApiParam(required=true) EnterpriseParam param) {
        param.setId(enterpriseId);
        return service.update(param);
    }

    @ApiOperation(value = "删除企业", notes = "")
    @DeleteMapping("{enterpriseId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer enterpriseId) {
        return service.delete(enterpriseId);
    }

}
