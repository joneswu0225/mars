package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.Enterprise;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.param.EnterpriseParam;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

@Log
@Service
public class EnterpriseService extends BaseService {
    @Autowired
    private EnterpriseMapper mapper;
    @Autowired
    private EnterpriseUserMapper enterpriseUserMapper;
    @Autowired
    private DepartmentUserMapper departmentUserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ProjectUserMapper projectUserMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private MessageService service;

    @Value("${app.file.path.pano}")
    private String panoBasePath;

    @PostConstruct
    private void init(){
        File file = new File(panoBasePath);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }

    @Transactional
    public BaseResponse add(EnterpriseParam param){
        mapper.insert(param);
        Integer enterpriseId = param.getId();
        File enterpriseFile = Paths.get(panoBasePath, enterpriseId.toString()).toFile();
        if(!enterpriseFile.exists()){
            enterpriseFile.mkdirs();
        }
        Enterprise enterprise = Enterprise.builder().basePath(File.separator + enterpriseId.toString()).build();
        enterprise.setId(enterpriseId);
        mapper.update(enterprise);
        return BaseResponse.builder().data(enterpriseUserMapper.findEnterpriseUser(enterpriseId)).build();
    }

    /**
     * 获取企业员工
     * @param enterpriseId
     * @return
     */
    public BaseResponse findEnterpriseUser(Integer enterpriseId){
        return BaseResponse.builder().data(enterpriseUserMapper.findEnterpriseUser(enterpriseId)).build();
    }

    /**
     * 企业加人
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addUser(EnterpriseUserParam param){
        try {
            enterpriseUserMapper.insert(param);
            service.sendInvitedToEnterprise(mapper.findOne(param.getEnterpriseId()).getName(), param.getUserId());
            if (param.getDepartmentId() != null) {
                departmentUserMapper.insert(DepartmentUserParam.builder().departmentId(param.getDepartmentId()).userIds(Arrays.asList(param.getUserId())));
            }
            return BaseResponse.builder().build();
        } catch (DuplicateKeyException e){
            return BaseResponse.builder().code(ErrorCode.ENT_USER_EXISTS).build();
        }
    }

    /**
     * 企业删人:
     * 1. 删除该企业下所有部门与该用户的所有关联
     * 2. 删除该企业下所有角色与该用户的所有关联
     * 3. 删除该企业与该用户的关联
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse removeUser(EnterpriseUserParam param){
        departmentUserMapper.delete(param);
        userRoleMapper.delete(param);
        enterpriseUserMapper.delete(param);
        projectUserMapper.deleteByEnterpriseUser(param);
        return BaseResponse.builder().build();
    }

}
