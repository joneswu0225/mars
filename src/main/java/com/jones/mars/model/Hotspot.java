package com.jones.mars.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Hotspot {
    private Integer id;
    private String code;
    private String title;
    private String name;
    private String icon;
    private Float location_x;
    private Float location_y;
    private Integer baseFlg;
    private Integer bhotspotId;
    private Integer sceneId;
    private Date createTime;
    private Date updateTime;
    private List<HotspotContent> baseContents = new ArrayList<>();
    private List<HotspotContent> contents = new ArrayList<>();
}

