package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.*;
import com.jones.mars.model.param.ProjectHotspotParam;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.query.ProjectUserQuery;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.param.ProjectUserParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectService extends BaseService {

    @Autowired
    private ProjectMapper mapper;
    @Autowired
    private BlockProjectMapper blockProjectMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private ProjectHotspotMapper projectHotspotMapper;
    @Autowired
    private ProjectSceneMapper projectSceneMapper;
    @Value("${app.public.block.id}")
    private Integer publicBlockId;

    @Autowired
    private MessageService service;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    /**
     * 新增项目：
     * 1. 新增项目内容
     * 2. 新增模块与项目关联关系
     * 3. 如果当前用户为普通用户，新增当前用户为项目负责人
     * 4. 新增项目共建人
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(ProjectParam param) {
        User currentUser = LoginUtil.getInstance().getUser();
        log.info("新增项目");
        param.setCreatorId(currentUser.getId());
        param.setStatus(Project.CREATING);
        mapper.insert(param);
        Integer projectId = param.getId();
        log.info("关联模块与项目关系");
        BlockProject blockProject = BlockProject.builder().blockId(param.getBlockId()).projectId(projectId).build();
        blockProjectMapper.insert(blockProject);
        if (currentUser.getUserType().equals(User.COMMON)) {
            log.info("当前用户为普通用户， 新增当前用户为项目负责人");
            projectUserMapper.insert(ProjectUserParam.builder().projectId(projectId).userIds(Arrays.asList(currentUser.getId())).managerFlg(ProjectUser.PROJECT_MANAGER).build());
        }
        List<Integer> userIds = param.getUserIds();
        if (!CollectionUtils.isEmpty(userIds)) {
            userIds.remove(currentUser.getId());
            log.info("添加项目共建人");
            if (!CollectionUtils.isEmpty(userIds)) {
                projectUserMapper.insert(ProjectUserParam.builder().projectId(projectId).userIds(userIds).build());
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", projectId);
        return BaseResponse.builder().data(result).build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse update(ProjectParam param) {
        log.info("更新项目{} {}", param.getId(), param.getName());
        param.setStatus(Project.EDITIND);
        mapper.update(param);
        Integer projectId = param.getId();
        log.info("添加项目共建人");
        if (!CollectionUtils.isEmpty(param.getUserIds())) {
            List<Integer> oriUserIds = projectUserMapper.findList(ProjectUserQuery.builder().projectId(projectId).managerFlg(ProjectUser.PROJECT_NORMAL).build()).stream().map(p->p.getUserId()).collect(Collectors.toList());
            param.getUserIds().removeAll(oriUserIds);
            if(param.getUserIds().size() > 0){
                projectUserMapper.insert(ProjectUserParam.builder().projectId(projectId).userIds(param.getUserIds()).build());
                service.sendAddToProject(param.getName(), param.getUserIds());
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", projectId);
        return BaseResponse.builder().data(result).build();
    }

    /**
     * 删除项目：
     * 1. 删除模块项目的关联关系
     * 2. 删除项目
     * //3. 删除项目热点关联
     * //4. 删除项目场景关联
     * @param projectId
     * @return
     */
    @Transactional
    public BaseResponse delete(Integer projectId){
        if(mapper.findOne(projectId).getStatus() == Project.CREATING) {
            blockProjectMapper.delete(projectId);
            mapper.delete(projectId);
            return BaseResponse.builder().build();
        } else {
            return BaseResponse.builder().code(ErrorCode.PROJECT_DELETE_DENIED).build();
        }
    }

    /**
     * 所有项目名称， 用于添加场景列表时筛选场景
     * @param query
     * @return
     */
    public BaseResponse allName(Query query){
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse findOne(Integer projectId){
        Project project = mapper.findOne(projectId);
        List<ProjectUser> projectUsers = projectUserMapper.findList(ProjectUserQuery.builder().projectId(projectId).build());
        project.setUserList(projectUsers);
        return BaseResponse.builder().data(project).build();
    }

    public BaseResponse addUser(ProjectUserParam param){
        projectUserMapper.insert(param);
        return BaseResponse.builder().data(param).build();
    }

    @Transactional
    public BaseResponse updateProjectManager(Integer projectId, Integer userId){
        projectUserMapper.update(ProjectUser.builder().projectId(projectId).managerFlg(ProjectUser.PROJECT_NORMAL).build());
        projectUserMapper.update(ProjectUser.builder().projectId(projectId).userId(userId).managerFlg(ProjectUser.PROJECT_MANAGER).build());
        service.sendAddToProjectManager(mapper.findOne(projectId).getName(), userId);
        return BaseResponse.builder().build();
    }

    public BaseResponse deleteUser(ProjectUser param){
        projectUserMapper.delete(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse submitVerifyProject(Integer projectId){
        BaseResponse response = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus().equals(Project.EDITIND)){
            Project updateProject = Project.builder().status(Project.VERIFYING).build();
            updateProject.setId(projectId);
            mapper.update(updateProject);
            Integer managerId = enterpriseMapper.findOne(project.getOriEnterpriseId()).getManagerId();
            service.sendSubmitVerifyProject(LoginUtil.getLoginSgname(), project.getName(), managerId);
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_VERIFIED);
        }
        return response;
    }

    public BaseResponse verifyProject(Integer projectId, Boolean isPass, String reason){
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus().equals(Project.VERIFYING)) {
            List<Integer> oriUserIds = projectUserMapper.findList(ProjectUserQuery.builder().projectId(projectId).managerFlg(ProjectUser.PROJECT_NORMAL).build()).stream().map(p->p.getUserId()).collect(Collectors.toList());
            if (isPass) {
                response = onshelfProject(projectId, false);
                service.sendVerifyPassProject(project.getName(), oriUserIds);
            } else {
                Project updateProject = Project.builder().status(Project.EDITIND).reason(reason).build();
                updateProject.setId(projectId);
                mapper.update(updateProject);
                service.sendVerifyFailProject(project.getName(), oriUserIds);
            }
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_VERIFIED);
        }
        return response;
    }

    public BaseResponse onshelfProject(Integer projectId, Boolean publicFlag){
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus() > Project.EDITIND) {
            project = Project.builder().status(Project.ONSHELF).publishDate(new Date()).build();
            project.setId(projectId);
            mapper.update(project);
            if (publicFlag != null && publicFlag) {
                blockProjectMapper.insert(BlockProject.builder().blockId(publicBlockId).projectId(projectId).build());
            }
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_ONSHELFED);
        }
        return response;
    }

    @Transactional
    public BaseResponse offshelfProject(Integer projectId){
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus().equals(Project.ONSHELF)){
            project = Project.builder().status(Project.DOWNSHELF).publishDate(new Date()).build();
            project.setId(projectId);
            mapper.update(project);
            unpublicProject(projectId);
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_OFFSHELFED);
        }
        return response;
    }
    @Transactional
    public BaseResponse unpublicProject(Integer projectId){
        Project project = Project.builder().publicFlg(Project.UNPUBLIC).build();
        project.setId(projectId);
        mapper.update(project);
        blockProjectMapper.delete(BlockProject.builder().blockId(publicBlockId).projectId(projectId).build());
        return BaseResponse.builder().build();
    }
    @Transactional
    public BaseResponse publicProject(Integer projectId){
        Project project = Project.builder().publicFlg(Project.PUBLIC).build();
        project.setId(projectId);
        mapper.update(project);
        blockProjectMapper.insert(BlockProject.builder().blockId(publicBlockId).projectId(projectId).build());
        return BaseResponse.builder().build();
    }

    public BaseResponse remodifyProject(Integer projectId) {
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus().equals(Project.DOWNSHELF)){
            project = Project.builder().status(Project.EDITIND).publishDate(new Date()).build();
            project.setId(projectId);
            mapper.update(project);
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_REMODIFY);
        }
        return response;
    }

    public BaseResponse insertProjectScene(ProjectSceneParam param) {
        projectSceneMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse deleteProjectScene(ProjectScene param){
        projectSceneMapper.delete(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateProjectSceneSeq(ProjectSceneParam param) {
        projectSceneMapper.updateProjectSceneSeq(param);
        return BaseResponse.builder().build();
    }
    public BaseResponse insertProjectHotspot(ProjectHotspotParam param) {
        projectHotspotMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse deleteProjectHotspot(ProjectHotspot param){
        projectHotspotMapper.delete(param);
        return BaseResponse.builder().build();
    }
    public BaseResponse updateProjectHotspotSeq(ProjectHotspotParam param) {
        projectHotspotMapper.updateProjectHotspotSeq(param);
        return BaseResponse.builder().build();
    }
}

