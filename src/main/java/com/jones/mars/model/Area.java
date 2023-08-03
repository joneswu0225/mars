package com.jones.mars.model;

import com.jones.mars.model.constant.City;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area extends BaseObject {
    private Integer provinceId;
    private String provinceName;
    private List<City> cityList = new ArrayList<>();
}

