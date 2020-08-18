package com.jones.mars.model;

import com.jones.mars.model.constant.FileType;
import com.jones.mars.object.BaseObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUpload extends BaseObject {
    private String path;
    private String name;
    private String domain;
    private Integer userId;
    private Integer relatedId;
    private FileType type;
}

