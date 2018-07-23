package com.jones.mars.repository;

import com.jones.mars.mapper.HotspotMapper;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.Query;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotspotRepository {

    @Autowired
    private HotspotMapper hotspotMapper;

    public Page<Hotspot> findByPage(Query query) {
        Integer count = findCount(query);
        return new Page(query, count, findList(query));
    }

    public List<Hotspot> findList(Query query) {
        return this.hotspotMapper.findList(query);
    }

    public List<Hotspot> findAll() {
        return this.hotspotMapper.findAll();
    }

    public Hotspot findOne(Integer id) {
        return this.hotspotMapper.findOne(id);
    }

    public Integer findCount(Query query) {
        return this.hotspotMapper.findCount(query);
    }

    public boolean existsCode(String code) {
        List list = this.hotspotMapper.findByCode(code);
        return list.size() > 0;
    }

    public void save(Hotspot hotspot) {
        if (hotspot.getHotspotId() == null)
            this.hotspotMapper.insert(hotspot);
        else
            this.hotspotMapper.update(hotspot);
    }

    public void delete(Integer hotspotId) {
        this.hotspotMapper.delete(hotspotId);
    }
}

