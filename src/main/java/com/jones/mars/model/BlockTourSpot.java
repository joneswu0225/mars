package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("block_tour_spot")
public class BlockTourSpot extends BaseObject {
    private Long id;
    private Long blockId;
    private String scode;
    private String acode;
    private String type;
    private String title;
    private String detail;
    private Float ath;
    private Float atv;
    private Float fov;
    private Integer seq;
}

