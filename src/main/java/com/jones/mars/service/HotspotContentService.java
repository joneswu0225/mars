package com.jones.mars.service;

import com.jones.mars.model.HotspotContent;
import com.jones.mars.model.param.HotspotContentParam;
import com.jones.mars.model.param.HotspotContentParams;
import com.jones.mars.model.param.HotspotContentSeqParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotContentMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public BaseResponse save(HotspotContentParams params){
        List<HotspotContentParam> updateParam = new ArrayList<>();
        List<HotspotContentParam> insertParam = new ArrayList<>();
        params.getHotspotContentList().forEach(p->{
            if(p.getId() != null) {
                updateParam.add(p);
            } else {
                insertParam.add(p);
            }
        });
        if(!CollectionUtils.isEmpty(updateParam)){
            mapper.update(HotspotContentParams.builder().hotspotContentList(updateParam).build());
        }
        if(!CollectionUtils.isEmpty(insertParam)){
            mapper.insert(HotspotContentParams.builder().hotspotContentList(insertParam).build());
        }
        return BaseResponse.builder().build();
    }

    public BaseResponse updateHotspotContentSeq(HotspotContentSeqParam param){
        mapper.updateHotspotContentSeq(param);
        return BaseResponse.builder().build();
    }

}


