package com.jones.mars.controller;

import com.jones.mars.model.News;
import com.jones.mars.model.Project;
import com.jones.mars.model.query.*;
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
    private EnterpriseService enterpriseService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectModuleService projectModuleService;
    @Autowired
    private BlockService blockService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserLikeService userLikeService;

    @ApiOperation(value = "企业详情", notes = "企业详情")
    @GetMapping("enterprise/{enterpriseId}")
    public BaseResponse findOneEnterprise(@PathVariable Long enterpriseId) {
        return enterpriseService.findById(enterpriseId);
    }

    @ApiOperation(value = "项目详情", notes = "项目详情")
    @GetMapping("project/{projectId}")
    public BaseResponse findOne(@PathVariable Long projectId) {
        return projectService.findOne(projectId);
    }

    @ApiOperation(value = "项目全景信息", notes = "项目全景信息")
    @GetMapping("project/{projectId}/panoInfo")
    public BaseResponse findPanoInfo(@PathVariable Long projectId, @ApiParam HotspotQuery query) {
        query.setProjectId(projectId);
        return sceneService.findPanoInfo(query);
    }

    @ApiOperation(value = "新闻详情", notes = "新闻详情")
    @GetMapping("news/{newsId}")
    public BaseResponse findNewsById(@PathVariable Long newsId) {
        return newsService.findById(newsId);
    }

    @ApiOperation(value = "新闻列表", notes = "发布中的新闻")
    @GetMapping("news")
    public BaseResponse findNewsList(@ApiParam NewsQuery query) {
        query.setStatus(News.STATUS_PUBLISHED);
        return newsService.findByPage(query);
    }

    @ApiOperation(value = "船福专题、全景船舶、全景设备", notes = "")
    @GetMapping("project")
    public BaseResponse project(@ApiParam ProjectQuery query) {
        query.setPublishFlg(Project.PUBLIC);
        return projectService.findByPage(query);
    }

    @ApiOperation(value = "一级分类列表", notes = "一级分类列表")
    @GetMapping("/module/list")
    public BaseResponse moduleList(@ApiParam ProjectModuleQuery query) {
        return projectModuleService.findAllProjectModule(query);
    }

    @ApiOperation(value = "模块列表", notes = "模块列表")
    @GetMapping("block")
    public BaseResponse list(@ApiParam BlockQuery query) {
        return blockService.findBlocks(query);
    }

    @ApiOperation(value = "跟据code查询模块详情", notes = "")
    @GetMapping("/block/code/{code}")
    public BaseResponse findOneByCode(@PathVariable String code) {
        return blockService.findOneByCode(code);
    }

    @ApiOperation(value = "查询模块详情", notes = "")
    @GetMapping("/block/{blockId}")
    public BaseResponse findOneByBlockId(@PathVariable Long blockId) {
        return blockService.findBlockById(blockId);
    }

    @ApiOperation(value = "所有场景列表", notes = "所有场景列表")
    @GetMapping("/scene/all")
    public BaseResponse all(@ApiParam SceneQuery query) {
        return sceneService.findAll(query);
    }

    @ApiOperation(value = "分页评论列表", notes = "分页评论列表")
    @GetMapping("comment")
    public BaseResponse list(@ApiParam CommentQuery query) {
        return commentService.findByPage(query);
    }

    @ApiOperation(value = "所有评论列表", notes = "所有评论列表")
    @GetMapping("comment/all")
    public BaseResponse all(@ApiParam CommentQuery query) {
        return commentService.findAll(query);
    }

    @ApiOperation(value = "点赞结果", notes = "点赞结果")
    @GetMapping("userLike/result")
    public BaseResponse userLikeResult(@ApiParam UserLikeQuery query) {
        return userLikeService.findLikeResult(query);
    }


}
