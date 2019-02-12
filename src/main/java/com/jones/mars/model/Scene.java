package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Scene extends BaseObject {
    private Integer sceneTypeId;
    private String code;
    private String name;
    private String detail;
    private Integer blockId;
    private Integer imageId;
    private String imageUrl;
    private Float locationX;
    private Float locationY;
    private Integer seq;
    private List<Hotspot> hotspots = new ArrayList<>();

//    public static Scene.SceneBuilder sceneBuilder(SceneParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).blockId(param.getBlockId())
//                .imageId(param.getImageId()).locationX(param.getLocationX()).locationY(param.getLocationY());
//    }
}
