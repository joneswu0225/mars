package com.jones.mars.controller;

import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.HomePageService;
import com.jones.mars.service.ProjectModuleService;
import com.jones.mars.service.ProjectService;
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
    private HomePageService service;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectModuleService projectModuleService;

    @ApiOperation(value = "获取船福专题、全景船舶、全景设备下所有子模块名称", notes = "")
    @GetMapping("moduleInfo")
    public BaseResponse moduleInfo() {
        return service.plateformEnterprise();
    }

    @ApiOperation(value = "获取船福推荐", notes = "")
    @GetMapping("recommend")
    public BaseResponse recommend() {
        return service.recommendProjects();
    }


    @ApiOperation(value = "项目详情", notes = "项目详情")
    @GetMapping("project/{projectId}")
    public BaseResponse findOne(@PathVariable Integer projectId) {
        return projectService.findById(projectId);
    }

    @ApiOperation(value = "船福专题、全景船舶、全景设备", notes = "")
    @GetMapping("project")
    public BaseResponse project(@ApiParam ProjectQuery query) {
        return projectService.findByPage(query);
    }

    @ApiOperation(value = "刷新首页缓存", notes = "")
    @GetMapping("refreshAll")
    public BaseResponse refreshBaseInfo(){
        service.refreshInitData();
        return new BaseResponse();
    }

}
