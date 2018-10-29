package com.jones.mars.model;

import com.jones.mars.model.constant.FileType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUpload {
    private String path;
    private String name;
    private Integer userId;
    private Integer relatedId;
    private FileType type;
}

