package com.jones.mars.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jones.mars.object.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("block_examine_content")
public class BlockExamineContent extends BaseObject {
    private String type;
    private Integer seq;
    private String extra;
    private String content;
    private String title;
    private Long examineId;
}

