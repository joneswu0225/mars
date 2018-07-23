package com.jones.mars.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by jones on 18-1-16.
 */
@Data
public class Question {
    private Integer questionId;
    private String hotspotCode;
    private String imgUrl;
    private String content;
    private Date inserttime;

}