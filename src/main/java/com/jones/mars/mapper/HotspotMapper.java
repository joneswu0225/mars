package com.jones.mars.mapper;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotMapper {
    List<Hotspot> findList(Query paramQuery);

    List<Hotspot> findAll();

    Hotspot findOne(Integer paramInteger);

    Integer findCount(Query paramQuery);

    List<Hotspot> findByCode(String paramString);

    void insert(Hotspot paramHotspot);

    void update(Hotspot paramHotspot);

    void delete(Integer paramInteger);
}

