package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class BlockImage {
    private String blockId;
    private String detail;
    private String name;
    private String path;
    private Integer id;
    private Date updateTime;
    private Date createTime;
}

