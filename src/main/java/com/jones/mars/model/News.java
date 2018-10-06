package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

@Data
public class News {
    private String content;
    private String author;
    private Date publishTime;
    private String summary;
    private String title;
    private String imageUrl;
    private Integer id;
}

