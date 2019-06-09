package com.jones.mars.service;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.ProjectScene;
import com.jones.mars.model.Scene;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.model.query.SceneQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotMapper;
import com.jones.mars.repository.ProjectSceneMapper;
import com.jones.mars.repository.SceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SceneService extends BaseService {
    @Autowired
    private SceneMapper sceneMapper;
    @Autowired
    private ProjectSceneMapper projectSceneMapper;
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
        return BaseResponse.builder().data(sceneMap.values()).build();
    }

    public BaseResponse insertProjectScene(ProjectSceneParam param) {
        projectSceneMapper.insert(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse deleteProjectScene(ProjectScene param){
        projectSceneMapper.delete(param);
        return BaseResponse.builder().build();
    }

    public BaseResponse updateProjectSceneSeq(ProjectSceneParam param) {
        projectSceneMapper.updateProjectSceneSeq(param);
        return BaseResponse.builder().build();
    }

}
