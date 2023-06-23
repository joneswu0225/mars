package com.jones.mars.model;

import com.jones.mars.object.BaseObject;
import lombok.Data;

@Data
public class BlockAttachmentContent extends BaseObject {
    private String type;
    private Integer seq;
    private String extra;
    private String content;
    private String title;
    private Integer attachmentId;
}

