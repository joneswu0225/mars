package com.jones.mars.controller;

import com.jones.mars.model.ProjectHotspot;
import com.jones.mars.model.ProjectScene;
import com.jones.mars.model.User;
import com.jones.mars.model.param.ProjectHotspotParam;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.param.ProjectUserParam;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.model.query.ProjectQuery;
import com.jones.mars.model.query.ProjectUserQuery;
import com.jones.mars.model.query.RolePermissionQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.service.BlockService;
import com.jones.mars.service.ProjectService;
import com.jones.mars.service.ProjectUserService;
import com.jones.mars.service.SceneService;
import com.jones.mars.util.LoginUtil;
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
    private BlockService blockService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private SceneService sceneService;

    /**
     * 如果当前用户为
     * operation: 1: 我的项目 0： 企业项目
     * 普通用户： operation=1：当前用户为共建人并且处于编辑状态的项目
     *          operation=0：处于发布状态的项目
     * @param query
     * @return
     */
    @ApiOperation(value = "项目列表", notes = "项目列表")
    @GetMapping("")
    public BaseResponse list(@ApiParam ProjectQuery query) {
        if(LoginUtil.getInstance().getUser().getUserType() == User.COMMON){
            query.setUserId(LoginUtil.getInstance().getUser().getId());
        }
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
        return projectService.findOne(projectId);
    }

    @ApiOperation(value = "项目全景信息", notes = "项目全景信息")
    @GetMapping("{projectId}/panoInfo")
    public BaseResponse findPanoInfo(@PathVariable Integer projectId, @ApiParam HotspotQuery query) {
        query.setProjectId(projectId);
        return sceneService.findPanoInfo(query);
    }

    @ApiOperation(value = "更新项目", notes = "更新项目")
    @PutMapping("{projectId}")
    public BaseResponse update(
            @PathVariable Integer projectId,
            @Valid @RequestBody @ApiParam(required=true) ProjectParam param) {
        param.setId(projectId);
        param.setReason("");
        return projectService.update(param);
    }

    @ApiOperation(value = "保存项目场景", notes = "保存项目场景,切换顺序也直接保存即可")
    @PostMapping("{projectId}/scene")
    public BaseResponse addScene(
            @PathVariable Integer projectId,
            @RequestBody @ApiParam(required=true) ProjectSceneParam param) {
        param.setProjectId(projectId);
        return projectService.insertProjectScene(param);
    }

    @ApiOperation(value = "调整项目场景顺序", notes = "")
    @PostMapping("/{projectId}/scene/changeSeq")
    public BaseResponse changeSceneSeq(@RequestBody @ApiParam(required=true) ProjectSceneParam param) {
        return projectService.updateProjectSceneSeq(param);
    }

    @ApiOperation(value = "删除项目场景", notes = "删除项目场景")
    @DeleteMapping("{projectId}/scene/{sceneId}")
    public BaseResponse deleteScene(
            @PathVariable Integer projectId,
            @PathVariable Integer sceneId) {
        return projectService.deleteProjectScene(ProjectScene.builder().projectId(projectId).sceneId(sceneId).build());
    }

    @ApiOperation(value = "调整热点顺序", notes = "")
    @PostMapping("/{projectId}/hotspot/changeSeq")
    public BaseResponse changeHotspotSeq(@RequestBody @ApiParam(required=true) ProjectHotspotParam param) {
        return projectService.updateProjectHotspotSeq(param);
    }

    @ApiOperation(value = "删除项目引导播放热点", notes = "删除项目引导播放热点")
    @DeleteMapping("{projectId}/hotspot/{hotspotId}")
    public BaseResponse deleteProjectHotspot(
            @PathVariable Integer projectId,
            @PathVariable Integer hotspotId) {
        return projectService.deleteProjectHotspot(ProjectHotspot.builder().projectId(projectId).hotspotId(hotspotId).build());
    }

    // TODO 增加后台注解
    @ApiOperation(value = "删除项目", notes = "")
    @DeleteMapping("{projectId}")
    public BaseResponse delete(@PathVariable @ApiParam(required=true) Integer projectId) {
        return projectService.delete(projectId);
    }

    @ApiOperation(value = "获取项目共建人", notes = "")
    @GetMapping("{projectId}/user")
    public BaseResponse findProjectPartner(@PathVariable @ApiParam(required=true) Integer projectId,
                                           @ApiParam(required=true) ProjectUserQuery query) {
        query.setProjectId(projectId);
        return projectUserService.findPage(query);
    }

    @ApiOperation(value = "获取项目共建人候选项-分页", notes = "")
    @GetMapping("{projectId}/user/validPartner")
    public BaseResponse findProjectPartnerAvailablePage(@PathVariable @ApiParam(required=true) Integer projectId,
                                                    @ApiParam(required=true) RolePermissionQuery query) {

        query.setProjectId(projectId);
        return projectService.findProjectUserValidPartnerPage(query);
    }

    @ApiOperation(value = "获取项目共建人候选项-全部", notes = "")
    @GetMapping("{projectId}/user/validPartner/all")
    public BaseResponse findProjectPartnerAvailableAll(@PathVariable @ApiParam(required=true) Integer projectId,
                                                       @ApiParam(required=true) RolePermissionQuery query) {
        query.setProjectId(projectId);
        return projectService.findProjectUserValidPartner(query);
    }

    @ApiOperation(value = "获取项目共建人", notes = "")
    @GetMapping("{projectId}/user/all")
    public BaseResponse findProjectPartnerAll(@PathVariable @ApiParam(required=true) Integer projectId,
                                           @ApiParam(required=true) ProjectUserQuery query) {
        query.setProjectId(projectId);
        return projectUserService.findAll(query);
    }

    @ApiOperation(value = "添加项目共建人", notes = "")
    @PostMapping("{projectId}/user")
    public BaseResponse addPartner(@PathVariable @ApiParam(required=true) Integer projectId,
                                   @RequestBody @ApiParam(required=true) ProjectUserParam param) {
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
        return projectService.deleteUser(ProjectUserParam.builder().projectId(projectId).userId(userId).build());
    }

    @ApiOperation(value = "上架项目", notes = "")
    @PatchMapping("{projectId}/onshelf")
    public BaseResponse publishProject(@PathVariable @ApiParam(required=true) Integer projectId,
                                       @RequestParam(name="publicFlag", required = false) @ApiParam(value="是否公开发布",name="publicFlag")Boolean publicFlag,
                                       @RequestParam(name="force", required = false) @ApiParam(value="是否无视状态强制更新",name="force")Integer force) {
        return  projectService.onshelfProject(projectId, publicFlag, force);
    }

    @ApiOperation(value = "提交审核项目", notes = "")
    @PatchMapping("{projectId}/submitVerify")
    public BaseResponse submitVerifyProject(@PathVariable @ApiParam(required=true) Integer projectId) {
        return  projectService.submitVerifyProject(projectId);
    }

    @ApiOperation(value = "下架的项目退回编辑", notes = "")
    @PatchMapping("{projectId}/remodify")
    public BaseResponse remodifyProject(@PathVariable @ApiParam(required=true) Integer projectId,
                                        @RequestParam(name="force", required = false) @ApiParam(value="是否无视状态强制退回",name="force")Integer force) {
        return  projectService.remodifyProject(projectId, force);
    }


    @ApiOperation(value = "审核项目", notes = "")
    @PatchMapping("{projectId}/verify")
    public BaseResponse verifyProject(@PathVariable @ApiParam(required=true) Integer projectId,
                                      @RequestParam(name="isPass", required = true) @ApiParam(value="是否通过",name="isPass")Boolean isPass,
                                      @RequestParam(name="reason", required = false) @ApiParam(value="不通过理由",name="reason")String reason) {
        return  projectService.verifyProject(projectId, isPass, reason);
    }

    @ApiOperation(value = "下架项目", notes = "")
    @PatchMapping("{projectId}/offshelf")
    public BaseResponse offshelfProject(@PathVariable @ApiParam(required=true) Integer projectId) {
        return  projectService.offshelfProject(projectId);
    }

    @ApiOperation(value = "公开项目", notes = "")
    @PatchMapping("{projectId}/public")
    public BaseResponse publicProject(@PathVariable @ApiParam(required=true) Integer projectId) {
        return  projectService.publicProject(projectId);
    }

    @ApiOperation(value = "取消公开项目", notes = "")
    @PatchMapping("{projectId}/unpublic")
    public BaseResponse unpublicProject(@PathVariable @ApiParam(required=true) Integer projectId) {
        return  projectService.unpublicProject(projectId);
    }


}
