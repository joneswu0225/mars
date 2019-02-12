package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

import java.util.Date;

@Data
public class News extends BaseObject {
    private String content;
    private String author;
    private Date publishTime;
    private String summary;
    private String title;
    private String imageUrl;
}

