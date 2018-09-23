package com.jones.mars.model;

import lombok.Data;

@Data
public class Scene {
    private Integer id;
    private Integer sceneTypeId;
    private String code;
    private String name;
    private String detail;
    private Integer imageId;
    private Float locationX;
    private Float locationY;
    private Integer seq;
}
