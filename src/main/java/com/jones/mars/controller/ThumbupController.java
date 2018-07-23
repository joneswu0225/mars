package com.jones.mars.controller;

import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Thumbup;
import com.jones.mars.service.ThumbupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/thumbup"})
public class ThumbupController extends BaseController {

    @Autowired
    private ThumbupService thumbupService;

    @ResponseBody
    @RequestMapping(value = {"/count"}, method = {RequestMethod.GET})
    public Integer list(@RequestParam(value = "hotspotCode", required = false) String hotspotCode, @RequestParam(value = "sceneCode", required = false) String sceneCode) {
        Thumbup thumbup = new Thumbup();
        thumbup.setSceneCode(sceneCode);
        thumbup.setHotspotCode(hotspotCode);
        return this.thumbupService.findCount(getQuery(thumbup));
    }

    @ResponseBody
    @RequestMapping(value = {"/add"}, method = {RequestMethod.POST})
    GeneralResponse addThumbup(@RequestParam(value = "hotspotCode", required = false) String hotspotCode, @RequestParam(value = "sceneCode", required = false) String sceneCode, @RequestParam(value = "userId", required = false) Integer userId) {
        Thumbup thumbup = new Thumbup();
        thumbup.setSceneCode(sceneCode);
        thumbup.setHotspotCode(hotspotCode);
        thumbup.setUserId(userId);
        this.thumbupService.saveThumbup(thumbup);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/cancel"}, method = {RequestMethod.POST})
    GeneralResponse cancelThumbup(@RequestParam(value = "sceneCode", required = false) String sceneCode, @RequestParam(value = "userId", required = false) Integer userId) {
        Thumbup thumbup = new Thumbup();
        thumbup.setSceneCode(sceneCode);
        thumbup.setUserId(userId);
        this.thumbupService.deleteThumbup(thumbup);
        return new GeneralResponse(true, "success");
    }
}

