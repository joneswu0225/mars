package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BlockImage extends BaseObject {
    private Integer blockId;
    private String detail;
    private String name;
    private String path;
    private Date updateTime;
    private Date createTime;

}

