package com.jones.mars.model;

import com.jones.mars.model.constant.City;
import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Area extends BaseObject {
    private Integer provinceId;
    private String provinceName;
    private List<City> cityList = new ArrayList<>();
}

