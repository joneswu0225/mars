package com.jones.mars.controller;

import com.jones.mars.model.Catalog;
import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.Style;
import com.jones.mars.repository.CatalogRepository;
import com.jones.mars.repository.StyleRepository;
import com.jones.mars.service.HotspotService;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/pano/hotspot"})
public class HotspotController extends BaseController {

    @Autowired
    private HotspotService hotspotService;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private StyleRepository styleRepository;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String list(@RequestParam(value = "title", required = false) String title,
                     @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        ModelMap map) {
        Hotspot hotspot = new Hotspot();
        hotspot.setTitle(title);
        Page<Hotspot> list = hotspotService.findByPage(getQuery(page, size, hotspot));
        Map<String, String> catalogMap = catalogRepository.findAll().stream().collect(Collectors.toMap(Catalog::getName, Catalog::getDetail));
        Map<String, String> styleMap = styleRepository.findAll().stream().collect(Collectors.toMap(Style::getCode, Style::getText));
        map.put("page", list);
        map.put("title", title);
        map.put("catalogMap", catalogMap);
        map.put("styleMap", styleMap);
        return "pano/hotspot/list";
    }

    @ResponseBody
    @RequestMapping(value = {"/save"}, method = {RequestMethod.POST})
    GeneralResponse saveHotspot(Hotspot hotspot) {
        hotspotService.saveHotspot(hotspot);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = {RequestMethod.POST})
    GeneralResponse saveHotspot(@RequestParam("hotspotId") Integer hotspotId) {
        hotspotService.delete(hotspotId);
        return new GeneralResponse(true, "success");
    }

    @ResponseBody
    @RequestMapping(value = {"/{hotspotId}"}, method = {RequestMethod.GET})
    Hotspot findOne(@PathVariable("hotspotId") Integer hotspotId) {
        return hotspotService.findOne(hotspotId);
    }
}
