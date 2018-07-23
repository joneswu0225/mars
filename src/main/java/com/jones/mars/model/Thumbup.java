package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class Thumbup {
    private Integer userId;
    private Integer thumbupId;
    private String hotspotCode;
    private String sceneCode;
    private Date insertTime;

}

