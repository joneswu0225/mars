package com.jones.mars.controller;

import com.jones.mars.model.param.CompanyJoinParam;
import com.jones.mars.model.param.CompanyJoinUpdateParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.CompanyJoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/companyJoin")
@Slf4j
@Api(value = "企业入驻", tags = {"企业入驻"})
public class CompanyJoinController extends BaseController {

    @Autowired
    private CompanyJoinService service;

    @ApiOperation(value = "入驻列表", notes = "入驻列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam Query query) {
        return service.findByPage(query);
    }

    @ApiOperation(value = "企业入驻", notes = "企业入驻")
    @PostMapping("")
    public BaseResponse join(@Valid @RequestBody @ApiParam(required=true) CompanyJoinParam param) {
        return service.join(param);
    }

    @ApiOperation(value = "更新企业入驻处理信息", notes = "添加备注")
    @PatchMapping("/{companyJoinId}")
    public BaseResponse updateStatus(
            @PathVariable Integer companyJoinId,
            @Valid @RequestBody @ApiParam(required=true) CompanyJoinUpdateParam param) {
        param.setId(companyJoinId);
        return service.updateStatus(param);
    }


}
