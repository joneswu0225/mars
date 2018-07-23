package com.jones.mars.service;

import com.jones.mars.model.GeneralResponse;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.Query;
import com.jones.mars.repository.HotspotRepository;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotspotService {

    @Autowired
    private HotspotRepository hotspotRepository;

    public Page<Hotspot> findByPage(Query<Hotspot> query) {
        return this.hotspotRepository.findByPage(query);
    }

    public GeneralResponse saveHotspot(Hotspot hotspot) {
        GeneralResponse resp = new GeneralResponse(false, "添加失败，请重试！");
        if ((this.hotspotRepository.existsCode(hotspot.getCode())) && (hotspot.getHotspotId() == null)) {
            resp.setMsg("code 已存在！");
        } else {
            resp = new GeneralResponse(true, "");
            this.hotspotRepository.save(hotspot);
        }
        return resp;
    }

    public List<Hotspot> findAll() {
        return this.hotspotRepository.findAll();
    }

    public Hotspot findOne(Integer id) {
        return this.hotspotRepository.findOne(id);
    }

    public void delete(Integer hotspotId) {
        this.hotspotRepository.delete(hotspotId);
    }
}

