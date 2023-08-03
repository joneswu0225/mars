package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Block extends BaseObject {
    private String detail;
    private String icon;
    private String imageUrl;
    private String code;
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
    private List<BlockContent> blockContentList = new ArrayList<>();

}

