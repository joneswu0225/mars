package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotspotContent extends BaseObject {
    public static final String MODULE_DEFAULT = "DEFAULT";
    private String type;
    private String module = MODULE_DEFAULT;
    private Integer seq;
    private String extra;
    private String content;
    private String title;
    private Integer hotspotId;
}

