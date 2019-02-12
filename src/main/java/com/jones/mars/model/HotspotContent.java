package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class HotspotContent extends BaseObject {
    private String type;
    private Integer seq;
    private String extra;
    private String content;
    private String title;
    private Integer hotspotId;
}

