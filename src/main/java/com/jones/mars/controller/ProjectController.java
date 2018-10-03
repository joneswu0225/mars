package com.jones.mars.controller;

import com.jones.mars.model.Project;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.param.ProjectSceneParam;
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
        return projectService.update(Project.projectBuilder(param).id(projectId).build());
    }

    @ApiOperation(value = "获取项目场景列表", notes = "获取项目场景列表")
    @GetMapping("{projectId}/scene")
    public BaseResponse findScene(
            @PathVariable Integer projectId) {
        return sceneService.findByProjectId(projectId);
    }

    @ApiOperation(value = "新增项目场景", notes = "新增项目场景")
    @PostMapping("{projectId}/scene")
    public BaseResponse addScene(
            @PathVariable Integer projectId,
            @ApiParam(required=true, name="sceneIds") Integer... sceneIds) {
        return sceneService.insertProjectScene(projectId, sceneIds);
    }

    @ApiOperation(value = "删除项目场景", notes = "删除项目场景")
    @DeleteMapping("{projectId}/scene/{sceneId}")
    public BaseResponse deleteScene(
            @PathVariable Integer projectId,
            @PathVariable Integer sceneId) {
        return sceneService.deleteProjectScene(ProjectSceneParam.builder().projectId(projectId).sceneId(sceneId).build());
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除项目", notes = "")
    @DeleteMapping("{projectId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer projectId) {
        return projectService.delete(projectId);
    }

    @ApiOperation(value = "添加项目共建人", notes = "")
    @PostMapping("{projectId}/partner")
    public BaseResponse addPartner(@PathVariable @ApiParam(required=true) Integer projectId,
            @RequestParam @ApiParam(required=true) Integer... userIds) {
        return projectService.addParners(projectId, userIds);
    }


    @ApiOperation(value = "修改项目负责人", notes = "")
    @PatchMapping("partner/{partnerId}")
    public BaseResponse updateProjectManager(@PathVariable @ApiParam(required=true) Integer partnerId,
                @RequestParam @ApiParam(required=true) Integer managerFlg) {
        return projectService.updatePartnerFlg(partnerId, managerFlg);
    }

    @ApiOperation(value = "删除项目共建人", notes = "")
    @DeleteMapping("partner/{partnerId}")
    public BaseResponse deletePartner(@PathVariable @ApiParam(required=true) Integer partnerId) {
        return projectService.deletePartner(partnerId);
    }


}
