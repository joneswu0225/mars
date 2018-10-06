package com.jones.mars.controller;

import com.jones.mars.model.Project;
import com.jones.mars.model.ProjectScene;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.param.ProjectUserParam;
import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.ProjectService;
import com.jones.mars.service.SceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
@Slf4j
@Api(value = "项目管理", tags = {"项目管理"})
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private SceneService sceneService;

    @ApiOperation(value = "项目列表", notes = "项目列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam ProjectQuery query) {
        return projectService.findByPage(query);
    }

    @ApiOperation(value = "项目名称列表", notes = "用于下拉菜单，如增加场景时根据项目ID来筛选")
    @GetMapping("allName")
    public BaseResponse allName(@ApiParam ProjectQuery query) {
        return projectService.allName(query);
    }

    @ApiOperation(value = "新增项目", notes = "新增项目")
    @PostMapping("")
    public BaseResponse add(@Valid @RequestBody @ApiParam(required=true) ProjectParam param) {
        return projectService.add(param);
    }

    @ApiOperation(value = "项目详情", notes = "项目详情")
    @GetMapping("{projectId}")
    public BaseResponse findOne(@PathVariable Integer projectId) {
        return projectService.findById(projectId);
    }

    @ApiOperation(value = "项目全景信息", notes = "项目全景信息")
    @GetMapping("{projectId}/panoInfo")
    public BaseResponse findPanoInfo(@PathVariable Integer projectId) {
        return sceneService.findPanoInfo(projectId);
    }

    @ApiOperation(value = "更新项目", notes = "更新项目")
    @PutMapping("{projectId}")
    public BaseResponse update(
            @PathVariable Integer projectId,
            @Valid @RequestBody @ApiParam(required=true) ProjectParam param) {
        param.setId(projectId);
        return projectService.update(param);
    }

//    @ApiOperation(value = "获取项目场景列表", notes = "获取项目场景列表")
//    @GetMapping("{projectId}/scene")
//    public BaseResponse findScene(
//            @PathVariable Integer projectId) {
//        return sceneService.findByProjectId(projectId);
//    }

    @ApiOperation(value = "新增项目场景", notes = "新增项目场景")
    @PostMapping("{projectId}/scene")
    public BaseResponse addScene(
            @PathVariable Integer projectId,
            @Valid @RequestBody @ApiParam(required=true) ProjectSceneParam param) {
        param.setProjectId(projectId);
        return sceneService.insertProjectScene(param);
    }

    @ApiOperation(value = "删除项目场景", notes = "删除项目场景")
    @DeleteMapping("{projectId}/scene/{sceneId}")
    public BaseResponse deleteScene(
            @PathVariable Integer projectId,
            @PathVariable Integer sceneId) {
        return sceneService.deleteProjectScene(ProjectScene.builder().projectId(projectId).sceneId(sceneId).build());
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除项目", notes = "")
    @DeleteMapping("{projectId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer projectId) {
        return projectService.delete(projectId);
    }

    @ApiOperation(value = "添加项目共建人", notes = "")
    @PostMapping("{projectId}/user")
    public BaseResponse addPartner(@PathVariable @ApiParam(required=true) Integer projectId,
                                   @Valid @RequestBody @ApiParam(required=true) ProjectUserParam param) {
        param.setProjectId(projectId);
        return projectService.addUser(param);
    }

    @ApiOperation(value = "修改项目负责人", notes = "")
    @PatchMapping("{projectId}/user/{userId}")
    public BaseResponse updateProjectManager(@PathVariable @ApiParam(required=true) Integer projectId,
                                             @PathVariable @ApiParam(required=true) Integer userId) {
        return projectService.updateProjectManager(projectId, userId);
    }

    @ApiOperation(value = "删除项目共建人", notes = "")
    @DeleteMapping("{projectId}/user/{userId}")
    public BaseResponse deletePartner(@PathVariable @ApiParam(required=true) Integer projectId,
                                      @PathVariable @ApiParam(required=true) Integer userId) {
        return projectService.deleteUser(ProjectUser.builder().projectId(projectId).userId(userId).build());
    }


}
