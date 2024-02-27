package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("hotspot")
public class Hotspot extends BaseObject {
    public static final String TYPE_DEFAULT = "DEFAULT";
    public static final String TYPE_GUIDE = "GUIDE";
    public static final String TYPE_ATTACHMENT = "ATTACHMENT";
    private String code;
    private String icon;
    private String sceneCode;
    private String sceneName;
    private String type = TYPE_DEFAULT;
    private String title;
    private Float locationX;
    private Float locationY;
    private Float locationFov;
    private String sceneId;
    private Integer seq;
    private String projectId;
    private Date createTime;
    private Date updateTime;
    private List<HotspotContent> contents = new ArrayList<>();

//    public static Hotspot.HotspotBuilder hotspotBuilder(HotspotParam param){
//        return builder().name(param.getName()).code(param.getCode()).title(param.getTitle()).locationX(param.getLocationX())
//                .locationY(param.getLocationY()).baseFlg(param.getBaseFlg()).bhotspotId(param.getBhotspotId()).sceneId(param.getSceneId());
//    }
}

