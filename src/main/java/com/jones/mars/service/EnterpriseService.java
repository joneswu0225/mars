package com.jones.mars.service;

import com.jones.mars.model.DepartmentUser;
import com.jones.mars.model.param.DepartmentParam;
import com.jones.mars.model.param.DepartmentUserParam;
import com.jones.mars.model.param.EnterpriseUserParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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

    @Override
    public BaseMapper getMapper() {
        return this.mapper;
    }


    public BaseResponse findEnterpriseUser(Integer enterpriseId){
        return BaseResponse.builder().data(enterpriseUserMapper.findEnterpriseUser(enterpriseId)).build();
    }

    /**
     * 企业加人
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addUser(EnterpriseUserParam param){
        enterpriseUserMapper.insert(param);
        if(param.getDepartmentId() != null){
            departmentUserMapper.insert(DepartmentUserParam.builder().departmentId(param.getDepartmentId()).userIds(Arrays.asList(param.getUserId())));
        }
        return BaseResponse.builder().build();
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
        return BaseResponse.builder().build();
    }

}
