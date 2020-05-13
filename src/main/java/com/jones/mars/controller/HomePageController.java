package com.jones.mars.controller;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.Project;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.*;
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
    private SceneService sceneService;
    @Autowired
    private NewsService newsService;

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

    @ApiOperation(value = "首页入驻品牌", notes = "")
    @GetMapping("brand")
    public BaseResponse enterpriserShown() {
        return service.enterpriseShown();
    }

    @ApiOperation(value = "首页服务优势", notes = "")
    @GetMapping("service")
    public BaseResponse serviceSuperiority() {
        return service.serviceSuperiority();
    }

    @ApiOperation(value = "首页信息", notes = "")
    @GetMapping("pageInfo")
    public BaseResponse pageInfo() {
        return service.homePageInfo();
    }

    @ApiOperation(value = "项目详情", notes = "项目详情")
    @GetMapping("project/{projectId}")
    public BaseResponse findOne(@PathVariable Integer projectId) {
        return projectService.findOne(projectId);
    }

    @ApiOperation(value = "项目全景信息", notes = "项目全景信息")
    @GetMapping("project/{projectId}/panoInfo")
    public BaseResponse findPanoInfo(@PathVariable Integer projectId, @ApiParam HotspotQuery query) {
        query.setProjectId(projectId);
        return sceneService.findPanoInfo(query);
    }

    @ApiOperation(value = "新闻详情", notes = "新闻详情")
    @GetMapping("news/{newsId}")
    public BaseResponse findNewsById(@PathVariable Integer newsId) {
        return newsService.findById(newsId);
    }

    @ApiOperation(value = "新闻列表", notes = "新闻列表")
    @GetMapping("news")
    public BaseResponse findNewsList(@ApiParam Query query) {
        return newsService.findByPage(query);
    }

    @ApiOperation(value = "app主页信息", notes = "app主页信息")
    @GetMapping("app/pageInfo")
    public BaseResponse appPageInfo() {
        return service.appPageInfo();
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
