package com.jones.mars.model;

import com.jones.mars.model.param.BlockImageParam;
import com.jones.mars.model.param.BlockParam;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BlockImage {
    private Integer id;
    private Integer blockId;
    private String detail;
    private String name;
    private String path;
    private Date updateTime;
    private Date createTime;

    public static BlockImage.BlockImageBuilder blockImageBuilder(BlockImageParam param){
        return builder().name(param.getName()).blockId(param.getBlockId()).detail(param.getDetail()).path(param.getPath());
    }
}

