package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("block_image")
public class BlockImage extends BaseObject {
    private String blockId;
    private String detail;
    private String name;
    private String path;
    private Date updateTime;
    private Date createTime;

}

