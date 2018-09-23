package com.jones.mars.model;

import com.jones.mars.model.param.NewsParam;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public News(){}

    public News(NewsParam param){
        this.title = param.getTitle();
        this.content = param.getContent();
        this.publishTime = param.getPublishTime();
        this.summary = param.getSummary();
        this.imageUrl = param.getImageUrl();
        this.author = param.getAuthor();
    }
}

