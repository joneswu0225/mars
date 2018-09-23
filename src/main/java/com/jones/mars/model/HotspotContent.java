package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class HotspotContent {
    private String type;
    private Integer seq;
    private String extra;
    private String content;
    private String title;
    private Integer hotspotId;
    private Integer id;
}

