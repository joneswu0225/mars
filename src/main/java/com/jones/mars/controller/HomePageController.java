package com.jones.mars.controller;

import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockService;
import com.jones.mars.service.EnterpriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@Slf4j
@Api(value = "主页管理", tags = {"主页管理"})
public class HomePageController{

    @Autowired
    private EnterpriseService enterpriseService;

    @ApiOperation(value = "获取船福专题、全景船舶、全景设备下所有子模块名称", notes = "")
    @GetMapping("moduleInfo")
    public BaseResponse moduleInfo() {
        return enterpriseService.plateformEnterprise();
    }

}
