package com.jones.mars.model;

import com.jones.mars.model.param.SceneParam;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Scene {
    private Integer id;
    private Integer sceneTypeId;
    private String code;
    private String name;
    private String detail;
    private Integer blockId;
    private Integer imageId;
    private Float locationX;
    private Float locationY;
    private Integer seq;
    private List<Hotspot> hotspots;

    public static Scene.SceneBuilder sceneBuilder(SceneParam param){
        return builder().name(param.getName()).detail(param.getDetail()).blockId(param.getBlockId())
                .imageId(param.getImageId()).locationX(param.getLocationX()).locationY(param.getLocationY());
    }
}
