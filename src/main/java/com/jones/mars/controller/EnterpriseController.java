package com.jones.mars.controller;

import com.jones.mars.model.EnterpriseUser;
import com.jones.mars.model.param.EnterpriseParam;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.model.query.EnterpriseUserQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.EnterpriseService;
import com.jones.mars.service.EnterpriseUserService;
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
    private EnterpriseService service;
    @Autowired
    private EnterpriseUserService enterpriseUserService;

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

    @ApiOperation(value = "人员部门列表", notes = "按人员划分部门")
    @GetMapping("/{enterpriseId}/userDepartment")
    public BaseResponse userlist(
            @PathVariable Integer enterpriseId,
            @ApiParam EnterpriseUserQuery query) {
        query.setEnterpriseId(enterpriseId);
        return enterpriseUserService.findByPage(query);
    }

    @ApiOperation(value = "企业所有员工列表", notes = "")
    @GetMapping("{enterpriseId}/user")
    public BaseResponse findEnterpriseUser(@PathVariable Integer enterpriseId) {
        return service.findEnterpriseUser(enterpriseId);
    }

    @ApiOperation(value = "企业添加员工", notes = "")
    @PostMapping("{enterpriseId}/user")
    public BaseResponse addUser(@PathVariable Integer enterpriseId,
                                @RequestBody @ApiParam(required=true) EnterpriseUserParam param) {
        param.setEnterpriseId(enterpriseId);
        return service.addUser(param);
    }

    @ApiOperation(value = "企业移除员工", notes = "")
    @DeleteMapping("{enterpriseId}/user/{userId}")
    public BaseResponse addUser(@PathVariable Integer enterpriseId,
                                @PathVariable Integer userId) {
        return service.removeUser(EnterpriseUserParam.builder().enterpriseId(enterpriseId).userId(userId).build());
    }

}
