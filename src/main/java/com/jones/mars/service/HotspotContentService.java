package com.jones.mars.service;

import com.jones.mars.model.param.HotspotContentSeqParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotspotContentService extends BaseService{

    @Autowired
    private HotspotContentMapper mapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    public BaseResponse findAll(Integer hotspotId){
        return BaseResponse.builder().data(mapper.findByHotspotId(hotspotId)).build();
    }

    public BaseResponse updateHotspotContentSeq(HotspotContentSeqParam param){
        mapper.updateHotspotContentSeq(param);
        return BaseResponse.builder().build();
    }

}


