package com.jones.mars.mapper;

import com.jones.mars.model.Hotspot;
import com.jones.mars.model.HsHs;
import com.jones.mars.model.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HsHsMapper {
    List<HsHs> findList(Query paramQuery);

    List<Hotspot> findInnerHotspotList(Query paramQuery);

    Integer findCount(Query paramQuery);

    HsHs findOne(Integer paramInteger);

    void insert(HsHs paramHsSc);

    void update(HsHs paramHsSc);

    void delete(Integer paramInteger);
}

