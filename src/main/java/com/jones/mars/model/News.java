package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News extends BaseObject {
    public static final int STATUS_EDITING = 0;
    public static final int STATUS_PUBLISHED = 1;
    public static final int STATUS_DOWNSHELF = 2;
    public static final int TOP_SET = 1;
    public static final int TOP_CANCEL = 0;


    private Integer id;
    private String contentUrl;
    private String contentHtml;
    private String author;
    private Date publishTime;
    private String summary;
    private String title;
    private Integer topFlg;
    private Integer status;
    private String institution;
    private String imageUrl;
    private Map<String, Integer> totalLikeInfo;
    private Map<String, Integer> privateLikeInfo;
}

