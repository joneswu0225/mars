package com.jones.mars.service;

import com.jones.mars.model.CompanyJoin;
import com.jones.mars.model.param.CompanyJoinParam;
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
        mapper.insert(CompanyJoin.companyJoinBuilder(param).build());
        return BaseResponse.builder().build();
    }

    /**
     * 更新企业入驻信息
     * @param remark
     * @return
     */
    public BaseResponse updateStaus(String remark){
        // 设置状态为已经处理
        mapper.update(CompanyJoin.builder().status(1).remark(remark).build());
        return BaseResponse.builder().build();
    }

}

