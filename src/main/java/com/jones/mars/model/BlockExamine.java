package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
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
public class BlockExamine extends BaseObject {
    private String name;
    private String detail;
    private String imageUrl;
    private Integer blockId;
    private Integer parentId;
    private Integer creatorId;
    private String type;
    private Integer seq;
    private Date updateTime;
    private Date createTime;
    private List<BlockExamineContent> blockExamineContentList = new ArrayList<>();

}

