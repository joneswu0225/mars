package com.jones.mars.service;

import com.jones.mars.model.BlockProject;
import com.jones.mars.model.Project;
import com.jones.mars.model.ProjectPartner;
import com.jones.mars.model.param.ProjectParam;
import com.jones.mars.model.query.Query;
import com.jones.mars.model.param.ProjectPartnerParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService extends BaseService {

    @Autowired
    private ProjectMapper mapper;
    @Autowired
    private BlockProjectMapper blockProjectMapper;
    @Autowired
    private ProjectPartnerMapper projectPartnerMapper;
    @Autowired
    private ProjectModuleMapper projectModuleMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(ProjectParam param){
        Project project = Project.projectBuilder(param).build();
        mapper.insert(project);
        System.out.println(project.getId());
        BlockProject blockProject = BlockProject.builder().blockId(param.getBlockId()).projectId(project.getId()).build();
        blockProjectMapper.insert(blockProject);
        // TODO 添加项目创建人为共建人（管理员）
        // Integer creatorId = 1;
//         projectPartnerMapper.insert(ProjectPartnerParam.builder().userIds(new Integer[]{creatorId}).projectId(project.getId()).managerFlg(ProjectPartner.PARTNER_MANAGER).build());
        return BaseResponse.builder().build();
    }

    public BaseResponse allName(Query query){
        List<Object> list = mapper.findAllName(query);
        return BaseResponse.builder().data(list).build();
    }

    public BaseResponse addParners(Integer projectId, Integer... userIds){
        projectPartnerMapper.insert(ProjectPartnerParam.builder().projectId(projectId).userIds(userIds).build());
        return BaseResponse.builder().build();
    }

    public BaseResponse updatePartnerFlg(Integer projectPartnerId, Integer managerFlg){
        projectPartnerMapper.update(ProjectPartner.builder().id(projectPartnerId).managerFlg(managerFlg).build());
        return BaseResponse.builder().build();
    }

    public BaseResponse deletePartner(Integer projectPartnerId){
        projectPartnerMapper.delete(ProjectPartner.builder().id(projectPartnerId).build());
        return BaseResponse.builder().build();
    }

}

