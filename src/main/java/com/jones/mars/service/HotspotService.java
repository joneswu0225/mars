package com.jones.mars.service;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.ProjectHotspot;
import com.jones.mars.model.param.HotspotParam;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotMapper;
import com.jones.mars.repository.ProjectHotspotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotspotService  extends BaseService{

    @Autowired
    private HotspotMapper mapper;
    @Autowired
    private ProjectHotspotMapper projectHotspotMapper;

    @Override
    public BaseMapper getMapper(){
        return this.mapper;
    }

    @Transactional
    public BaseResponse add(HotspotParam param){
        mapper.insert(param);
        if(param.getType().equals(Hotspot.TYPE_GUIDE)) {
            projectHotspotMapper.insert(ProjectHotspot.builder().hotspotId(param.getId()).projectId(param.getProjectId()));
        }
        return BaseResponse.builder().data(param.getId()).build();
    }

    @Transactional
    public BaseResponse delete(Integer hotspotId){
        mapper.delete(hotspotId);
        if(mapper.findOne(hotspotId).getType().equals(Hotspot.TYPE_GUIDE)){
            projectHotspotMapper.delete(hotspotId);
        }
        return BaseResponse.builder().data(hotspotId).build();
    }

}


