package com.jones.mars.service;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.Scene;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.model.param.ProjectSceneParam;
import com.jones.mars.model.query.SceneQuery;
import com.jones.mars.object.BaseResponse;
import com.jones.mars.repository.BaseMapper;
import com.jones.mars.repository.HotspotMapper;
import com.jones.mars.repository.ProjectSceneMapper;
import com.jones.mars.repository.SceneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BaseResponse findByProjectId(Integer projectId){
        return BaseResponse.builder().data(sceneMapper.findAll(SceneQuery.builder().projectId(projectId).build())).build();
    }

    public BaseResponse findPanoInfo(Integer projectId){
        List<Scene> sceneList = sceneMapper.findAll(SceneQuery.builder().projectId(projectId).build());
        Map<Integer, Scene> sceneMap = sceneList.stream().sorted(Comparator.comparing(Scene::getSeq)).collect(Collectors.toMap(Scene::getId, p->p));
        List<Hotspot> hotspotList = hotspotMapper.findAll(HotspotQuery.builder().sceneIds(sceneMap.keySet()).build());
        hotspotList.forEach(p->sceneMap.get(p.getSceneId()).getHotspots().add(p));
        return BaseResponse.builder().data(sceneMap.values()).build();
    }

    @Transactional
    public BaseResponse insertProjectScene(Integer projectId, Integer... sceneIds) {
        ProjectSceneParam  param = ProjectSceneParam.builder().projectId(projectId).build();
        for (int i=0; i<sceneIds.length; i++) {
            param.setSceneId(sceneIds[i]);
            param.setSeq(i);
            projectSceneMapper.insert(param);
        }
        return BaseResponse.builder().build();
    }

    @Transactional
    public BaseResponse insertProjectScene(List<ProjectSceneParam> params){
        for(ProjectSceneParam param : params){
            projectSceneMapper.insert(param);
        }
        /*Integer projectId = params.get(0).getProjectId();
        SceneQuery query = new SceneQuery();
        query.setProjectId(projectId);
        List<Scene> list = sceneMapper.findAll(query);
        List<Integer> sceneIds = list.stream().map(p->p.getId()).collect(Collectors.toList());
        for(ProjectSceneParam param : params){
            projectSceneMapper.insert(param);
            sceneIds.remove(param.getSceneId());
        }
        ProjectScene projectScene = new ProjectScene();
        projectScene.setProjectId(projectId);
        for(Integer sceneId : sceneIds){
            projectScene.setSceneId(sceneId);
            projectSceneMapper.delete(projectScene);
        }*/
        return BaseResponse.builder().build();
    }

    public BaseResponse deleteProjectScene(ProjectSceneParam param){
        projectSceneMapper.delete(param);
        return BaseResponse.builder().build();
    }











}
