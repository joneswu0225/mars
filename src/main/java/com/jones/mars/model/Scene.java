package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("scene")
public class Scene extends BaseObject {
    public static final int UNPUBLIC = 0;
    public static final int PUBLIC = 1;
    public static final int SLICESTATUS_TODO = 0;
    public static final int SLICESTATUS_FINISH = 1;

    private Long sceneTypeId;
    private String sceneTypeName;
    private String code;
    private String name;
    private String detail;
    private Long blockId;
    private Long srcBlockId;
    private Long imageId;
    private String imageUrl;
    private String panoImageUrl;
    private Float locationX;
    private Float locationY;
    private Integer seq;
    private Integer sliceStatus;
    private Integer publicFlg = UNPUBLIC;
    private Long relSceneTypeId;
    private String relSceneTypeName;
    private List<Hotspot> hotspots = new ArrayList<>();

//    public static Scene.SceneBuilder sceneBuilder(SceneParam param){
//        return builder().name(param.getName()).detail(param.getDetail()).blockId(param.getBlockId())
//                .imageId(param.getImageId()).locationX(param.getLocationX()).locationY(param.getLocationY());
//    }
}
