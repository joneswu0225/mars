package com.jones.mars.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.jones.mars.constant.ErrorCode;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.ProjectScene;
import com.jones.mars.model.Scene;
import com.jones.mars.model.constant.CommonConstant;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.model.param.*;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.model.query.SceneQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotMapper;
import com.jones.mars.repository.ProjectSceneMapper;
import com.jones.mars.repository.SceneMapper;
import com.jones.mars.util.KrpanoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SceneService extends BaseService {
    @Autowired
    private SceneMapper sceneMapper;
    @Autowired
    private HotspotMapper hotspotMapper;

    @Override
    public BaseMapper getMapper() {
        return this.sceneMapper;
    }

    public BaseResponse findAll(SceneQuery query){
        return BaseResponse.builder().data(sceneMapper.findAll(query)).build();
    }

    public BaseResponse allName(SceneQuery query){
        return BaseResponse.builder().data(sceneMapper.findAllName(query)).build();
    }

    public BaseResponse insertScene(SceneParam param){
        Integer maxSeq = sceneMapper.findMaxSeqByBlockId(param.getBlockId());
        param.setSeq(maxSeq == null ? 0 : maxSeq + 1);
        sceneMapper.insert(param);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", param.getId());
        return BaseResponse.builder().data(map).build();
    }

    public BaseResponse findPanoInfo(HotspotQuery query){
        List<Scene> sceneList = sceneMapper.findAll(SceneQuery.builder().projectId(query.getProjectId()).build());
        Map<Integer, Scene> sceneMap = sceneList.stream().sorted(Comparator.comparing(Scene::getSeq)).collect(Collectors.toMap(Scene::getId, p->p));
        if(sceneMap.size() > 0){
//            List<Hotspot> hotspotList = hotspotMapper.findAll(HotspotQuery.builder().sceneIds(sceneMap.keySet()).build());
            List<Hotspot> hotspotList = hotspotMapper.findAllByQuery(query);
            hotspotList.forEach(p->{
                if(sceneMap.containsKey(p.getSceneId())){
                    sceneMap.get(p.getSceneId()).getHotspots().add(p);
                }
            });
        }
        List<Scene> result = sceneMap.values().stream().sorted(Comparator.comparing(Scene::getSeq)).collect(Collectors.toList());
        return BaseResponse.builder().data(result).build();
    }


    public BaseResponse sliceImageBySceneId(Integer sceneId, KrpanoUtil.PanoType panoType){
        Scene scene = sceneMapper.findOne(sceneId);
        return sliceImage(scene, panoType);
    }

    public BaseResponse sliceImageBySceneCode(String sceneCode, KrpanoUtil.PanoType panoType){
        List<Scene> sceneList = sceneMapper.findAll(SceneQuery.builder().sceneCode(sceneCode).build());
        return sliceImage(sceneList.get(0), panoType);
    }

    public BaseResponse sliceImageByBlock(Integer blockId, KrpanoUtil.PanoType panoType){
        List<Scene> sceneList = sceneMapper.findAll(SceneQuery.builder().blockId(blockId).build());
        sceneList = sceneList.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPanoImageUrl())).collect(Collectors.toList());
        Map<String, String> result = new HashMap<>();
        if(sceneList.size() > 0) {
            for(Scene scene: sceneList) {
                BaseResponse resp = sliceImage(scene, panoType);
                result.put(scene.getCode(), resp.getMessage());
            }
        }
        return BaseResponse.builder().data(result).build();
    }

    private BaseResponse sliceImage(Scene scene, KrpanoUtil.PanoType panoType){
        scene.setSliceStatus(Scene.SLICESTATUS_TODO);
        sceneMapper.update(scene);
        ErrorCode code = KrpanoUtil.slice(CommonConstant.UPLOAD_PATH + File.separator + scene.getPanoImageUrl(), scene.getBlockId(), panoType);
        if(code.isSucceeded()){
            scene.setSliceStatus(Scene.SLICESTATUS_FINISH);
            sceneMapper.update(scene);
        }
        return BaseResponse.builder().code(code).build();
    }


    public BaseResponse updateSceneSeq(SceneSeqParam param){
        sceneMapper.updateSceneSeq(param);
        return BaseResponse.builder().build();
    }


}
