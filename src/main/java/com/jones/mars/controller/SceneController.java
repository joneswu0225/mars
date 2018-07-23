package com.jones.mars.controller;

import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Scene;
import com.jones.mars.service.SceneService;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/pano/scene"})
public class SceneController extends BaseController {
    @Autowired
    private SceneService sceneService;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String list(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "size", required = false, defaultValue = "20") int size, @RequestParam(value = "page", required = false, defaultValue = "0") int page, ModelMap map) {
        Scene scene = new Scene();
        scene.setTitle(title);
        Page list = this.sceneService.findByPage(getQuery(page, size, scene));
        map.put("page", list);
        map.put("title", title);
        return "pano/scene/list";
    }

    @ResponseBody
    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    GeneralResponse saveScene(Scene scene) {
        GeneralResponse resp = this.sceneService.saveScene(scene);
        return resp;
    }
}

