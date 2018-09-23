package com.jones.mars.model;

import com.jones.mars.model.constant.City;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Area {
    private Integer provinceId;
    private String provinceName;
    private List<City> cityList = new ArrayList<>();
}

