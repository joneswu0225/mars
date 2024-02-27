package com.jones.mars.service;

import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.EnterpriseUser;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.DepartmentUsers;
import com.jones.mars.model.param.EnterpriseParam;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public CommonMapper getMapper() {
        return this.mapper;
    }

    @Transactional
    public BaseResponse add(EnterpriseParam param){
        mapper.insert(param);
        if(param.getManagerId() != null){
            enterpriseUserMapper.insert(EnterpriseUser.builder().enterpriseId(param.getId()).userId(param.getManagerId()).managerFlg(EnterpriseUser.ENTERPRISE_MANAGER).build());
        }
        String enterpriseId = param.getId();
        return BaseResponse.builder().data(enterpriseUserMapper.findEnterpriseUser(enterpriseId)).build();
    }

    /**
     * 获取企业员工
     * @param enterpriseId
     * @return
     */
    public BaseResponse findEnterpriseUser(String enterpriseId){
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
    public BaseResponse removeUser(EnterpriseUser param){
        departmentUserMapper.deleteByEnterpriseUser(param);
        userRoleMapper.deleteByEnterpriseUser(param);
        enterpriseUserMapper.delete(param);
        projectUserMapper.deleteByEnterpriseUser(param);
        return BaseResponse.builder().build();
    }

}
