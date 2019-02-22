package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.BlockProject;
import com.jones.mars.model.Project;
import com.jones.mars.model.ProjectUser;
import com.jones.mars.model.User;
import com.jones.mars.model.param.ProjectParam;
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
    private ProjectUserMapper projectUserMapper;
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
        mapper.update(param);
        Integer projectId = param.getId();
        log.info("添加项目共建人");
        if (!CollectionUtils.isEmpty(param.getUserIds())) {
            projectUserMapper.insert(ProjectUserParam.builder().projectId(projectId).userIds(param.getUserIds()).build());
            // TODO 找到新增的然后发消息
            List<Integer> userIds = projectUserMapper.findByProjectId(projectId).stream().map(p->p.getUserId()).collect(Collectors.toList())
//            service.sendAddToProject(mapper.get);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", projectId);
        return BaseResponse.builder().data(result).build();
    }

    /**
     * 删除项目：
     * 1. 删除模块项目的关联关系
     * 2. 删除项目
     * @param projectId
     * @return
     */
    @Transactional
    public BaseResponse delete(Integer projectId){
        blockProjectMapper.delete(projectId);
        mapper.delete(projectId);
        return BaseResponse.builder().build();
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
        List<ProjectUser> projectUsers = projectUserMapper.findByProjectId(projectId);
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
            project = Project.builder().status(Project.VERIFYING).build();
            project.setId(projectId);
            mapper.update(project);
        } else {
            response.setErrorCode(ErrorCode.PROJECT_VERIFY_VERIFIED);
        }
        return response;
    }

    public BaseResponse verifyProject(Integer projectId, Boolean isPass, String reason){
        BaseResponse response  = BaseResponse.builder().build();
        Project project = mapper.findOne(projectId);
        if(project.getStatus().equals(Project.VERIFYING)) {
            if (isPass) {
                return onshelfProject(projectId, false);
            } else {
                project = Project.builder().status(Project.NOTPASS).reason(reason).build();
                project.setId(projectId);
                mapper.update(project);
                return BaseResponse.builder().build();
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

}

