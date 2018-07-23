package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class HsSc {
    private Integer hsScId;
    private String hotspotCode;
    private Hotspot hotspot;
    private String sceneCode;
    private float ath;
    private float atv;
    private Integer roleId;
    private Date insertTime;
}

