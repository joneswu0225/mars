package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Block extends BaseObject {
    private String detail;
    private String icon;
    private String imageUrl;
    private Integer operatorId;
    private Date updateTime;
    private Date createTime;
    private String status;
    private Integer enterpriseId;
    private String name;
    private String panoPath;
    private Integer seq;
    private List<BlockModule> moduleList = new ArrayList<>();
    private List<BlockHotspot> blockHotspotList = new ArrayList<>();

}

