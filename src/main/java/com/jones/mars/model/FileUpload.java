package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.model.constant.FileType;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("file_upload")
public class FileUpload extends BaseObject {
    private String path;
    private String name;
    private String domain;
    private Long userId;
    private Long relatedId;
    private FileType type;
}

