package com.jones.mars.controller;

import com.jones.mars.model.*;
import com.jones.mars.service.HotspotService;
import com.jones.mars.service.HsHsService;
import com.jones.mars.service.SceneService;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/pano/hshs"})
public class HsHsController extends BaseController {

    @Autowired
    private HotspotService hotspotService;
    @Autowired
    private SceneService sceneService;
    @Autowired
    private HsHsService hsHsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(value = "pHotspotCode", required = false) String pHotspotCode,
                       @RequestParam(value = "hotspotCode", required = false) String hotspotCode,
                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                       ModelMap map) {
        HsHs hsHs = new HsHs();
        hsHs.setpHotspotCode(pHotspotCode);
        hsHs.setHotspotCode(hotspotCode);
        Page<HsHs> list = hsHsService.findByPage(getQuery(page, size, hsHs));
        Map<String, String> hotspotMap = hotspotService.findAll().stream().collect(Collectors.toMap(Hotspot::getCode, Hotspot::getTitle));
        map.put("page", list);
        map.put("hotspotCode", hotspotCode);
        map.put("pHotspotCode", pHotspotCode);
        map.put("hotspotMap", hotspotMap);
        return "pano/hshs/list";
    }

    @ResponseBody
    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    GeneralResponse saveHsHs(HsHs hsHs) {
        return hsHsService.saveScSc(hsHs);

    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = {RequestMethod.POST})
    GeneralResponse deleteHsHs(@RequestParam("hsHsId") Integer hsHsId) {
        hsHsService.delete(hsHsId);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/{hsHsId}"}, method = {RequestMethod.GET})
    HsHs findOne(@PathVariable("hsHsId") Integer hsHsId) {
        return hsHsService.findOne(hsHsId);
    }
}
