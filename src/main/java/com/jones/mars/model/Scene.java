package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Scene extends BaseObject {
    public static final int UNPUBLIC = 0;
    public static final int PUBLIC = 1;
    public static final int SLICESTATUS_TODO = 0;
    public static final int SLICESTATUS_FINISH = 1;

    private Integer sceneTypeId;
    private String sceneTypeName;
    private String code;
    private String name;
    private String detail;
    private Integer blockId;
    private Integer imageId;
    private String imageUrl;
    private String panoImageUrl;
    private Float locationX;
    private Float locationY;
    private Integer seq;
    private Integer sliceStatus;
    private Integer publicFlg = UNPUBLIC;
    private List<Hotspot> hotspots = new ArrayList<>();

//    public static Scene.SceneBuilder sceneBuilder(SceneParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).blockId(param.getBlockId())
//                .imageId(param.getImageId()).locationX(param.getLocationX()).locationY(param.getLocationY());
//    }
}
