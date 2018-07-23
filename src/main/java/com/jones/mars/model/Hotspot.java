package com.jones.mars.model;

import lombok.Data;

import java.util.Date;
@Data
public class Hotspot {
    private Integer hotspotId;
    private String code;
    private String catalogName;
    private String title;
    private String content;
    private float ath;
    private float atv;
    private String onclick = "";
    private String onload = "";
    private String hover = "";
    private String out = "";
    private String styleName;
    private String sceneCode;
    private String pHotspotCode;
    private Date insertTime;
}

