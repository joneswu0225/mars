package com.jones.mars.repository;

import com.jones.mars.model.AppConst;
import com.jones.mars.model.constant.City;
import com.jones.mars.model.param.AppConstParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppConstMapper extends BaseMapper<AppConst> {
    List<AppConst> findGroup();
    void updateAppConstSeq(AppConstParam param);
}
