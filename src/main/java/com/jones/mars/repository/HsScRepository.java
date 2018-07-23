package com.jones.mars.repository;

import com.jones.mars.mapper.HsScMapper;
import com.jones.mars.model.Hotspot;
import com.jones.mars.model.HsSc;
import com.jones.mars.model.Query;
import com.jones.mars.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HsScRepository {

    @Autowired
    private HsScMapper hsScMapper;

    public Page<HsSc> findByPage(Query query) {
        Integer count = findCount(query);
        return new Page(query, count, findList(query));
    }

    public Page<Hotspot> findHotspotByPage(Query query) {
        Integer count = findCount(query);
        return new Page(query, count, findHotspotList(query));
    }

    public List<HsSc> findList(Query query) {
        return this.hsScMapper.findList(query);
    }

    public List<Hotspot> findHotspotList(Query query) {
        return this.hsScMapper.findHotspotList(query);
    }

    public Integer findCount(Query query) {
        return this.hsScMapper.findCount(query);
    }

    public HsSc findOne(Integer id) {
        return this.hsScMapper.findOne(id);
    }

    public void save(HsSc hsSc) {
        if (hsSc.getHsScId() == null)
            this.hsScMapper.insert(hsSc);
        else
            this.hsScMapper.update(hsSc);
    }

    public void delete(Integer hsScId) {
        this.hsScMapper.delete(hsScId);
    }
}

