package com.jones.mars.model;

import com.jones.mars.model.param.HotspotParam;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Hotspot {
    private Integer id;
    private String code;
    private String title;
    private String name;
    private Float locationX;
    private Float locationY;
    private Integer baseFlg;
    private Integer bhotspotId;
    private Integer sceneId;
    private Date createTime;
    private Date updateTime;
    private List<HotspotContent> baseContents = new ArrayList<>();
    private List<HotspotContent> contents = new ArrayList<>();

    public static Hotspot.HotspotBuilder hotspotBuilder(HotspotParam param){
        return builder().name(param.getName()).code(param.getCode()).title(param.getTitle()).locationX(param.getLocationX())
                .locationY(param.getLocationY()).baseFlg(param.getBaseFlg()).bhotspotId(param.getBhotspotId()).sceneId(param.getSceneId());
    }
}

