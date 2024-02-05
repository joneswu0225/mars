package com.jones.mars.repository;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.query.HotspotQuery;
import com.jones.mars.model.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotMapper extends CommonMapper<Hotspot> {
    List<Object> findAllName(Query query);
    List<Hotspot> findAllByQuery(HotspotQuery query);
}

