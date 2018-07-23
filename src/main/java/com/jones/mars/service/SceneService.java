package com.jones.mars.service;

import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Query;
import com.jones.mars.model.Scene;
import com.jones.mars.repository.SceneRepository;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneService {

    @Autowired
    private SceneRepository sceneRepository;

    public Page<Scene> findByPage(Query<Scene> query) {
        return sceneRepository.findByPage(query);
    }

    public List<Scene> findList(Query<Scene> query) {
        return sceneRepository.findList(query);
    }

    public GeneralResponse saveScene(Scene scene) {
        GeneralResponse resp = new GeneralResponse(false, "添加失败，请重试！");
        if (scene.getSceneId() == null && sceneRepository.existsTitle(scene.getTitle())) {
            resp.setMsg("title 已存在！");
        } else {
            resp = new GeneralResponse(true, "");
            sceneRepository.save(scene);
        }
        return resp;
    }

    public List<Scene> findAll() {
        return sceneRepository.findAll();
    }
}

