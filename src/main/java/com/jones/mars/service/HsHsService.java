package com.jones.mars.service;

import com.jones.mars.model.*;
import com.jones.mars.repository.HotspotRepository;
import com.jones.mars.repository.HsHsRepository;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class HsHsService {

    @Autowired
    private HsHsRepository hsHsRepository;

    @Autowired
    private HotspotRepository hotspotRepository;

    public Page<HsHs> findByPage(Query<HsSc> query) {
        return hsHsRepository.findByPage(query);
    }

    public Page<Hotspot> findHotspotByPage(Query<HsHs> query) {
        return hsHsRepository.findInnerHotspotByPage(query);
    }

    public List<Hotspot> findInnerHotspotList(Query<HsHs> query) {
        return hsHsRepository.findInnerHotspotList(query);
    }

    public GeneralResponse saveScSc(HsHs hsHs) {
        GeneralResponse resp = new GeneralResponse(false, "添加失败，请重试！");
        resp = new GeneralResponse(true, "");
        hsHsRepository.save(hsHs);
        return resp;
    }

    public GeneralResponse addInnerHotspot(Hotspot hotspot) {
        GeneralResponse resp = new GeneralResponse(false, "添加失败，请重试！");
        resp = new GeneralResponse(true, "");
        if (StringUtils.isEmpty(hotspot.getCode())) {
            hotspot.setCode(System.currentTimeMillis() + "");
        }
        hotspotRepository.save(hotspot);
        HsHs hsHs = new HsHs();
        hsHs.setAth(hotspot.getAth());
        hsHs.setAtv(hotspot.getAtv());
        hsHs.setHotspotCode(hotspot.getCode());
        hsHs.setpHotspotCode(hotspot.getPHotspotCode());
        hsHsRepository.save(hsHs);
        return resp;
    }

    public HsHs findOne(Integer id) {
        return hsHsRepository.findOne(id);
    }

    public void delete(Integer hsScId) {
        hsHsRepository.delete(hsScId);
    }
}

