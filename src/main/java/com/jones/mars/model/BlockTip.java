package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("block_tip")
public class BlockTip extends BaseObject {
    private String id;
    private String spotId;
    private String blockId;
    private String detail;
    private String spotBlockId;
    private String scode;
    private String acode;
    private String type;
    private String title;
    private String spotDetail;
    private Float ath;
    private Float atv;
    private Float fov;
    private Integer seq;
}

