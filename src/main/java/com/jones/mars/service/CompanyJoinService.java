package com.jones.mars.service;

import com.jones.mars.model.CompanyJoin;
import com.jones.mars.model.param.CompanyJoinParam;
import com.jones.mars.model.param.CompanyJoinUpdateParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.CompanyJoinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyJoinService extends BaseService{

    @Autowired
    private CompanyJoinMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }
    /**
     * 企业入驻
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse join(CompanyJoinParam param){
        mapper.insert(param);
        return BaseResponse.builder().build();
    }

    /**
     * 更新企业入驻信息
     * @param param
     * @return
     */
    public BaseResponse updateStatus(CompanyJoinUpdateParam param){
        mapper.update(CompanyJoin.builder().status(param.getStatus()).remark(param.getRemark()).build());
        return BaseResponse.builder().build();
    }

}

