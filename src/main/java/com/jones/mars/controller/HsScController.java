package com.jones.mars.controller;

import com.jones.mars.model.*;
import com.jones.mars.service.HotspotService;
import com.jones.mars.service.HsScService;
import com.jones.mars.service.SceneService;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/pano/hssc"})
public class HsScController extends BaseController {

    @Autowired
    private HotspotService hotspotService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private HsScService hsScService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "sceneCode", required = false) String sceneCode,
                       @RequestParam(value = "hotspotCode", required = false) String hotspotCode,
                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                       ModelMap map) {
        HsSc hsSc = new HsSc();
        hsSc.setSceneCode(sceneCode);
        hsSc.setHotspotCode(hotspotCode);
        Page<HsSc> list = hsScService.findByPage(getQuery(page, size, hsSc));
        Map<String, String> hotspotMap = hotspotService.findAll().stream().collect(Collectors.toMap(Hotspot::getCode, Hotspot::getTitle));
        Map<String, String> sceneMap = sceneService.findAll().stream().collect(Collectors.toMap(Scene::getCode, Scene::getTitle));
        map.put("page", list);
        map.put("hotspotCode", hotspotCode);
        map.put("sceneCode", sceneCode);
        map.put("sceneMap", sceneMap);
        map.put("hotspotMap", hotspotMap);
        return "pano/hssc/list";
    }

    @ResponseBody
    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    GeneralResponse saveHssc(HsSc hsSc) {
        hsScService.saveHsSc(hsSc);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = {RequestMethod.POST})
    GeneralResponse saveHotspot(@RequestParam("hsScId") Integer hsScId) {
        hsScService.delete(hsScId);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/{hsScId}"}, method = {RequestMethod.GET})
    HsSc findOne(@PathVariable("hsScId") Integer hsScId) {
        return hsScService.findOne(hsScId);
    }
}
