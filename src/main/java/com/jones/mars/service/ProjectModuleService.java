package com.jones.mars.service;

import com.jones.mars.model.param.BlockModuleParam;
import com.jones.mars.model.query.ProjectModuleQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.BlockModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectModuleService extends BaseService{

    @Autowired
    private BlockModuleMapper blockModuleMapper;
    @Override
    public CommonMapper getMapper(){
        return this.blockModuleMapper;
    }

    public BaseResponse findAllProjectModule(ProjectModuleQuery query){
        return BaseResponse.builder().data(blockModuleMapper.findAll(query)).build();
    }

    public BaseResponse updateBlockModuleSeq(BlockModuleParam param){
        blockModuleMapper.updateBlockModuleSeq(param);
        return BaseResponse.builder().build();
    }


}

