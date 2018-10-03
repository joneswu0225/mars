package com.jones.mars.model;

import com.jones.mars.model.constant.FileType;
import lombok.Data;

@Data
public class FileUpload {
    private String path;
    private String name;
    private Integer relatedId;
    private FileType fileType;
}

