package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.*;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.constant.TaskType;
import com.jones.mars.model.param.*;
import com.jones.mars.model.query.*;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import com.jones.mars.util.LoginUtil;
import com.jones.mars.util.Page;
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
    private TaskMapper taskMapper;
    @Autowired
    private BlockProjectMapper blockProjectMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private ProjectHotspotMapper projectHotspotMapper;
    @Autowired
    private ProjectSceneMapper projectSceneMapper;
    @Autowired
    private HotspotMapper hotspotMapper;
    @Value("${app.public.block.id:3}")
    private Integer publicBlockId;

    private static final Integer FORCE_EXECUTE = 1;


    @Autowired
    private MessageService service;
    @Autowired
    private ProjectUserService projectUserService;

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
//        if (currentUser.getUserType().equals(User.COMMON)) {
        log.info("新增当前用户为项目负责人");
        projectUserMapper.insert(ProjectUserParam.builder().projectId(projectId).userIds(Arrays.asList(currentUser.getId())).managerFlg(ProjectUser.PROJECT_MANAGER).build());
//        }
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


    public ErrorCode projectModifyAuthError(Integer projectId){
        Project project = mapper.findOne(projectId);
        User loginUser = LoginUtil.getInstance().getUser();
        if(loginUser.getUserType().equals(User.COMMON)){
            // 被取消编辑授权的也要报错
            List<RolePermission> permissions = rolePermissionMapper.findAll(RolePermissionQuery.builder().operation(RolePermission.CREATE).classId(project.getClassId()).userId(loginUser.getId()).build());
            if(permissions.size() == 0){
                log.info("用户[ %s ] 因无编辑权限无法修改项目信息", loginUser.getMobile());
                return ErrorCode.AUTH_PROJECT_EDIT_UNAUTH;
            }
            Integer count = projectUserMapper.findCount(ProjectUserQuery.builder().projectId(project.getId()).userId(loginUser.getId()).build());
            if(count == 0){
                log.info("用户[ %s ] 因非共建人无法修改项目信息", loginUser.getMobile());
                return ErrorCode.AUTH_PROJECT_EDIT_NOTPARTNER;
            }
        } else if(loginUser.getUserType().equals(User.ENTMANAGER)) {
            List<Integer> enterpriseIds = loginUser.getEnterprises().stream().map(p->p.getId()).collect(Collectors.toList());
            if(!enterpriseIds.contains(project.getOriEnterpriseId())) {
                log.info("用户[ %s ] 因非该企业管理员无法修改项目信息", loginUser.getMobile());
                return ErrorCode.AUTH_PROJECT_EDIT_UNAUTH;
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse update(ProjectParam param) {
//        ErrorCode projectModifyAuthError = projectModifyAuthError(param.getId());
//        if(projectModifyAuthError != null){
//            return BaseResponse.builder().code(projectModifyAuthError).build();
//        }
        Integer force = param.getForce() == null ? 0 : param.getForce();
        Project project = mapper.findOne(param.getId());
        if(force.equals(FORCE_EXECUTE) && project.getStatus().equals(Project.ONSHELF)){
            return BaseResponse.builder().code(ErrorCode.PROJECT_MODIFY_DENIED_PUBLISHED).build();
        }
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
//        ErrorCode projectModifyAuthError = projectModifyAuthError(projectId);
//        if(projectModifyAuthError != null){
//            return BaseResponse.builder().code(projectModifyAuthError).build();
//        }
        if(mapper.findOne(projectId).getStatus() == Project.CREATING) {
            blockProjectMapper.delete(projectId);
            taskMapper.deleteCurrentTask(TaskParam.builder().projectId(projectId).currentFlg(Task.CURRENT_TASK).build());
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

    public BaseResponse findProjectUserValidPartnerPage(RolePermissionQuery query){
        List<ProjectUser> userList = rolePermissionMapper.findGrantedUserInfoList(query);
        Integer count = rolePermissionMapper.findGrantedUserInfoCount(query);
        Page<ProjectUser> userPage = new Page(query, count, userList);
        return BaseResponse.builder().data(userPage).build();
    }

    public BaseResponse findProjectUserValidPartner(RolePermissionQuery query){
        List<ProjectUser> userList = rolePermissionMapper.findGrantedUserInfoAll(query);
        return BaseResponse.builder().data(userList).build();
    }

    //TODO split authority
    public BaseResponse findOne(Integer projectId){
        Project project = mapper.findOne(projectId);
        User loginUser = LoginUtil.getInstance().getUser();
        if(project.getPublicFlg().equals(Project.UNPUBLIC) && project.getEnterprisePlateformFlg().equals(CommonConstant.NOPLATEFROM)){
            if(loginUser == null) {
                return BaseResponse.builder().code(ErrorCode.AUTH_PROJECT_UNPUBLIC_NOLOGIN).build();
            } else {
                if(loginUser.getUserType().equals(User.COMMON)){
                    List<RolePermission> permissions = rolePermissionMapper.findAll(RolePermissionQuery.builder().classId(project.getClassId()).userId(loginUser.getId()).build());
                    if(permissions.size() == 0){
                        return BaseResponse.builder().code(ErrorCode.AUTH_PROJECT_UNAUTH).build();
                    }
                } else if(loginUser.getUserType().equals(User.ENTMANAGER)) {
                    List<Integer> enterpriseIds = loginUser.getEnterprises().stream().map(p->p.getId()).collect(Collectors.toList());
                    if(!enterpriseIds.contains(project.getOriEnterpriseId())) {
                        return BaseResponse.builder().code(ErrorCode.AUTH_PROJECT_UNAUTH).build();
                    }
                }
            }
        }
        if(loginUser != null){
            List<ProjectUser> projectUsers = projectUserService.findAllData(ProjectUserQuery.builder().projectId(projectId).build());
            project.setUserList(projectUsers);
        }
        List<Hotspot> attachments = hotspotMapper.findAllByQuery(HotspotQuery.builder().projectId(projectId).type(Hotspot.TYPE_ATTACHMENT).build());
        List<Hotspot> guidances = hotspotMapper.findAllByQuery(HotspotQuery.builder().projectId(projectId).type(Hotspot.TYPE_GUIDE).hasSceneCode(true).build());
        project.setAttachments(attachments);
        project.setGuidances(guidances);
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

    @Transactional
    public BaseResponse deleteUser(ProjectUserParam param){
        //删除任务时区分任务类型
        taskMapper.deleteCurrentTask(TaskParam.builder().currentFlg(Task.CURRENT_TASK).projectId(param.getProjectId()).userId(param.getUserId()).build());
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

    @Transactional
    public BaseResponse verifyProject(Integer projectId, Boolean isPass, String reason){
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus().equals(Project.VERIFYING)) {
            List<Integer> oriUserIds = projectUserMapper.findList(ProjectUserQuery.builder().projectId(projectId).managerFlg(ProjectUser.PROJECT_NORMAL).build()).stream().map(p->p.getUserId()).collect(Collectors.toList());
            if (isPass) {
                response = onshelfProject(projectId, false);
                // 获取项目当前处理中的任务
                List<Task> tasks = taskMapper.findAll(TaskQuery.builder().projectId(projectId).currentFlg(Task.CURRENT_TASK).build());
                for(Task task: tasks){
                    taskMapper.update(TaskParam.builder().id(task.getId()).status(Task.FINISHED).build());
                }
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
        return onshelfProject(projectId, publicFlag, FORCE_EXECUTE);
    }

    public BaseResponse onshelfProject(Integer projectId, Boolean publicFlag, Integer force){
        force = force == null ? 0 : force;
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus() > Project.EDITIND | (force > 0)) {
            project = Project.builder().status(Project.ONSHELF).publishDate(new Date()).build();
            project.setId(projectId);
            if (publicFlag != null && publicFlag) {
                project.setPublicFlg(Project.PUBLIC);
                blockProjectMapper.insert(BlockProject.builder().blockId(publicBlockId).projectId(projectId).build());
            }
            mapper.update(project);
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
//        blockProjectMapper.delete(BlockProject.builder().blockId(publicBlockId).projectId(projectId).build());
        return BaseResponse.builder().build();
    }

    @Transactional
    public BaseResponse backToEditProject(Integer projectId){
        Project project = Project.builder().status(Project.EDITIND).build();
        project.setId(projectId);
        mapper.update(project);
        return BaseResponse.builder().build();
    }

    @Transactional
    public BaseResponse publicProject(Integer projectId){
        Project project = Project.builder().publicFlg(Project.PUBLIC).build();
        project.setId(projectId);
        mapper.update(project);
//        blockProjectMapper.insert(BlockProject.builder().blockId(publicBlockId).projectId(projectId).build());
        return BaseResponse.builder().build();
    }

    public BaseResponse remodifyProject(Integer projectId, Integer force) {
        force = force == null ? 0 : force;
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(force > 0  || project.getStatus().equals(Project.DOWNSHELF)){
            project = Project.builder().status(Project.EDITIND).publishDate(new Date()).build();
            project.setId(projectId);
            mapper.update(project);
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_REMODIFY);
        }
        return response;
    }

    public BaseResponse insertProjectScene(ProjectSceneParam param) {
        if(param.getSceneIds() != null && param.getSceneIds().size() == 1){
            Integer maxSeq = projectSceneMapper.findMaxSeqByProjectId(param.getProjectId());
            param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
            param.setSceneId(param.getSceneIds().get(0));
            projectSceneMapper.insertOne(param);
            return BaseResponse.builder().data(param.getId()).build();
        }else{
            projectSceneMapper.insert(param);
        }
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

    public BaseResponse deleteProjectHotspot(ProjectHotspot param){
        projectHotspotMapper.delete(param);
        return BaseResponse.builder().build();
    }
    public BaseResponse updateProjectHotspotSeq(ProjectHotspotParam param) {
        projectHotspotMapper.updateProjectHotspotSeq(param);
        return BaseResponse.builder().build();
    }
}

