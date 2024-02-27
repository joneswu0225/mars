package com.jones.mars.service;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.ProjectHotspot;
import com.jones.mars.model.ProjectHotspots;
import com.jones.mars.model.param.HotspotParam;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.CommonMapper;
import com.jones.mars.repository.HotspotContentMapper;
import com.jones.mars.repository.HotspotMapper;
import com.jones.mars.repository.ProjectHotspotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotspotService  extends BaseService{

    @Autowired
    private HotspotMapper mapper;
    @Autowired
    private ProjectHotspotMapper projectHotspotMapper;
    @Autowired
    private HotspotContentMapper hotspotContentMapper;

    @Override
    public CommonMapper getMapper(){
        return this.mapper;
    }

    @Transactional
    public BaseResponse add(HotspotParam param){
        mapper.insert(param);
        if(param.getType().equals(Hotspot.TYPE_GUIDE) || param.getType().equals(Hotspot.TYPE_ATTACHMENT)) {
            Integer maxSeq = projectHotspotMapper.findMaxSeqByHotspot(Hotspot.builder().projectId(param.getProjectId()).type(param.getType()).build());
            projectHotspotMapper.insert(ProjectHotspot.builder().hotspotId(param.getId()).seq(maxSeq == null ? 0 : (maxSeq + 1)).projectId(param.getProjectId()).build());
        }
        Map<String, String> result = new HashMap<>();
        result.put("id", param.getId().toString());
        result.put("code", param.getCode());
        return BaseResponse.builder().data(result).build();
    }

    @Transactional
    public BaseResponse delete(String hotspotId){
        if(mapper.findOne(hotspotId).getType().equals(Hotspot.TYPE_GUIDE)){
            projectHotspotMapper.delete(hotspotId);
        }
        hotspotContentMapper.deleteByHotspotId(hotspotId);
        mapper.delete(hotspotId);
        return BaseResponse.builder().data(hotspotId).build();
    }

    public BaseResponse findAllByQuery(HotspotQuery query){
        List<Hotspot> hotspotList = mapper.findAllByQuery(query);
        return BaseResponse.builder().data(hotspotList).build();
    }

}


